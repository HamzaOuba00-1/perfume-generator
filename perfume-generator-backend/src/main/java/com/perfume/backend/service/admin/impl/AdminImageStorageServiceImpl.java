package com.perfume.backend.service.admin.impl;

import com.perfume.backend.exception.BusinessException;
import com.perfume.backend.service.admin.AdminImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Service
public class AdminImageStorageServiceImpl implements AdminImageStorageService {

    private final Path uploadPath;
    private static final long MAX_BYTES = 2 * 1024 * 1024; // 2MB

    public AdminImageStorageServiceImpl(
            @Value("${app.upload.oils-dir}") String uploadDir) {

        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Impossible de créer le dossier d'upload : " + uploadPath, e);
        }
    }

    @Override
    public boolean oilImageExists(String filename) {
        if (filename == null)
            return false;
        Path p = uploadPath.resolve(filename).normalize();
        return p.startsWith(uploadPath) && Files.exists(p) && Files.isRegularFile(p);
    }

    @Override
    public String storeOilImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("Fichier image invalide.");
        }
        if (file.getSize() > MAX_BYTES) {
            throw new BusinessException("Image trop volumineuse (max 2MB).");
        }

        // 1) Vérif MIME annoncé
        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/png")
                || contentType.equals("image/jpeg")
                || contentType.equals("image/webp"))) {
            throw new BusinessException("Type d'image non autorisé (png/jpg/webp).");
        }

        // 2) Vérif contenu réel : doit être décodable comme image
        try {
            BufferedImage img = ImageIO.read(file.getInputStream());
            if (img == null) {
                throw new BusinessException("Fichier non reconnu comme image.");
            }
        } catch (IOException e) {
            throw new BusinessException("Erreur de lecture de l'image.");
        }

        // 3) Extension forcée côté serveur
        String extension = switch (contentType) {
            case "image/png" -> ".png";
            case "image/jpeg" -> ".jpg";
            case "image/webp" -> ".webp";
            default -> ".png";
        };

        String filename = UUID.randomUUID() + extension;
        Path targetLocation = this.uploadPath.resolve(filename).normalize();

        // 4) Empêcher toute sortie du dossier (défense en profondeur)
        if (!targetLocation.startsWith(this.uploadPath)) {
            throw new BusinessException("Chemin invalide.");
        }

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            throw new BusinessException("Erreur lors de l'enregistrement de l'image.");
        }
    }

    @SuppressWarnings("unused")
    private String getExtension(String name) {
        if (name == null || !name.contains(".")) {
            return ".png";
        }
        return name.substring(name.lastIndexOf('.'));
    }
}
