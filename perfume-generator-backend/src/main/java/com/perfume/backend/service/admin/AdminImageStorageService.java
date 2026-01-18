package com.perfume.backend.service.admin;

import org.springframework.web.multipart.MultipartFile;

public interface AdminImageStorageService {

    /**
     * Stocke une image d'huile essentielle
     * @param file fichier image
     * @return nom du fichier stocké
     */
    String storeOilImage(MultipartFile file);
}
