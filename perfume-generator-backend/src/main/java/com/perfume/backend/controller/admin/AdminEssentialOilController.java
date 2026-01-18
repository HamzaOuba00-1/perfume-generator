package com.perfume.backend.controller.admin;

import com.perfume.backend.dto.admin.CreateEssentialOilRequest;
import com.perfume.backend.dto.response.EssentialOilDto;
import com.perfume.backend.service.admin.AdminEssentialOilService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST réservé à l'administration.
 * Permet la gestion des huiles essentielles.
 */
@RestController
@RequestMapping("/api/admin/oils")
public class AdminEssentialOilController {

    private final AdminEssentialOilService adminEssentialOilService;

    public AdminEssentialOilController(AdminEssentialOilService adminEssentialOilService) {
        this.adminEssentialOilService = adminEssentialOilService;
    }

    /**
     * ➕ Ajouter une nouvelle huile essentielle
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EssentialOilDto createOil(
            @RequestBody @Valid CreateEssentialOilRequest request) {

        return adminEssentialOilService.createOil(request);
    }

    /**
     * ✏️ Modifier une huile existante
     */
    @PutMapping("/{id}")
    public EssentialOilDto updateOil(
            @PathVariable Long id,
            @RequestBody @Valid CreateEssentialOilRequest request) {

        return adminEssentialOilService.updateOil(id, request);
    }

    /**
     * 🗑️ Supprimer une huile essentielle
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOil(@PathVariable Long id) {
        adminEssentialOilService.deleteOil(id);
    }

    @GetMapping("/ping")
    public void ping() {
        // si on arrive ici => auth OK
    }
}
