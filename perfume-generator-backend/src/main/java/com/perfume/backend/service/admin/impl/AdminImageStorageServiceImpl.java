package com.perfume.backend.service.admin.impl;

import com.perfume.backend.exception.BusinessException;
import com.perfume.backend.service.admin.AdminImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class AdminImageStorageServiceImpl implements AdminImageStorageService {

    private final Path uploadPath;

    public AdminImageStorageServiceImpl(
            @Value("${app.upload.oils-dir}") String uploadDir) {

        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new IllegalStateException(
                "Impossible de créer le dossier d'upload : " + uploadPath, e
            );
        }
    }

    @Override
    public String storeOilImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new BusinessException("Fichier image invalide.");
        }

        try {
            String extension = getExtension(file.getOriginalFilename());
            String filename = UUID.randomUUID() + extension;

            Path targetLocation = this.uploadPath.resolve(filename);

            Files.copy(
                file.getInputStream(),
                targetLocation,
                StandardCopyOption.REPLACE_EXISTING
            );

            return filename;

        } catch (IOException e) {
            throw new BusinessException(
                "Erreur lors de l'enregistrement de l'image : " + e.getMessage()
            );
        }
    }

    private String getExtension(String name) {
        if (name == null || !name.contains(".")) {
            return ".png";
        }
        return name.substring(name.lastIndexOf('.'));
    }
}
