package com.perfume.backend.domain.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Représente une recette de parfum générée par le moteur métier.
 * Contient la composition finale (huile essentielle -> pourcentage).
 *
 * Objet métier pur (non persisté en base).
 */
public class PerfumeRecipe {

    /**
     * Composition du parfum.
     * La clé est l'huile essentielle,
     * la valeur est le pourcentage attribué.
     */
    private final Map<EssentialOil, Integer> composition;

    public PerfumeRecipe(Map<EssentialOil, Integer> composition) {
        // Protection contre les modifications externes
        this.composition = new LinkedHashMap<>(composition);
    }

    /**
     * Retourne une vue non modifiable de la composition.
     */
    public Map<EssentialOil, Integer> getComposition() {
        return Collections.unmodifiableMap(composition);
    }

    /**
     * Vérifie que la somme des pourcentages est égale à 100.
     * Utile pour les validations et les tests.
     */
    public int getTotalPercentage() {
        return composition.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
