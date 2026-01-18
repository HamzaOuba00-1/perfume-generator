package com.perfume.backend.service.admin;

import com.perfume.backend.dto.admin.CreateEssentialOilRequest;
import com.perfume.backend.dto.response.EssentialOilDto;

/**
 * Service applicatif réservé à l'administration.
 * Gère la création, modification et suppression
 * des huiles essentielles.
 */
public interface AdminEssentialOilService {

    /**
     * ➕ Créer une nouvelle huile essentielle.
     *
     * @param request données de création
     * @return huile créée (DTO)
     */
    EssentialOilDto createOil(CreateEssentialOilRequest request);

    /**
     * ✏️ Mettre à jour une huile essentielle existante.
     *
     * @param id identifiant de l'huile
     * @param request nouvelles données
     * @return huile mise à jour (DTO)
     */
    EssentialOilDto updateOil(Long id, CreateEssentialOilRequest request);

    /**
     * 🗑️ Supprimer une huile essentielle.
     *
     * @param id identifiant de l'huile
     */
    void deleteOil(Long id);
}
