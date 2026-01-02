package com.perfume.backend.service;

import com.perfume.backend.domain.model.PerfumeRecipe;

import java.util.List;

/**
 * Service métier pour la génération de parfums.
 */
public interface PerfumeService {

    /**
     * Génère une recette de parfum à partir
     * d'une liste de noms d'huiles essentielles.
     *
     * @param oilNames noms des huiles sélectionnées par l'utilisateur
     * @return recette de parfum générée
     */
    PerfumeRecipe generatePerfume(List<String> oilNames);
}
