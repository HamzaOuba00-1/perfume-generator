package com.perfume.backend.controller.admin;

import com.perfume.backend.service.admin.AdminImageStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/oils")
public class AdminImageUploadController {

    private final AdminImageStorageService storageService;

    public AdminImageUploadController(AdminImageStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(
        value = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Map<String, String>> uploadImage(
            @RequestPart("file") MultipartFile file) {

        String filename = storageService.storeOilImage(file);

        return ResponseEntity.ok(
                Map.of("imageUrl", filename)
        );
    }
}
