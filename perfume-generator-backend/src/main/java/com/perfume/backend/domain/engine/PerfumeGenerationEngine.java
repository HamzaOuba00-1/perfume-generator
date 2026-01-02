package com.perfume.backend.domain.engine;

import com.perfume.backend.domain.model.EssentialOil;
import com.perfume.backend.domain.model.NoteType;
import com.perfume.backend.domain.model.PerfumeRecipe;
import com.perfume.backend.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Moteur métier déterministe de génération de parfum.
 */
@Component
public class PerfumeGenerationEngine {

    private static final int TETE_PERCENT = 25;
    private static final int COEUR_PERCENT = 35;
    private static final int FOND_PERCENT = 40;

    public PerfumeRecipe generate(List<EssentialOil> oils) {

        validateStructure(oils);

        Map<NoteType, List<EssentialOil>> grouped = groupByNoteType(oils);

        Map<EssentialOil, Integer> result = new LinkedHashMap<>();

        distribute(result, grouped.get(NoteType.TETE), TETE_PERCENT);
        distribute(result, grouped.get(NoteType.COEUR), COEUR_PERCENT);
        distribute(result, grouped.get(NoteType.FOND), FOND_PERCENT);

        normalize(result);

        return new PerfumeRecipe(result);
    }

    // =========================
    // VALIDATION STRUCTURELLE
    // =========================
    private void validateStructure(List<EssentialOil> oils) {

        boolean hasTete = oils.stream().anyMatch(o -> o.getNoteType() == NoteType.TETE);
        boolean hasCoeur = oils.stream().anyMatch(o -> o.getNoteType() == NoteType.COEUR);
        boolean hasFond = oils.stream().anyMatch(o -> o.getNoteType() == NoteType.FOND);

        if (!hasTete || !hasCoeur || !hasFond) {
            throw new BusinessException(
                    "Le parfum doit contenir au moins une note de tête, de cœur et de fond.");
        }
    }

    // =========================
    // GROUPAGE PAR NOTE
    // =========================
    private Map<NoteType, List<EssentialOil>> groupByNoteType(List<EssentialOil> oils) {

        Map<NoteType, List<EssentialOil>> map = new EnumMap<>(NoteType.class);

        for (NoteType type : NoteType.values()) {
            map.put(type, new ArrayList<>());
        }

        for (EssentialOil oil : oils) {
            map.get(oil.getNoteType()).add(oil);
        }

        return map;
    }

    // =========================
    // DISTRIBUTION INTERNE
    // =========================
    private void distribute(Map<EssentialOil, Integer> result,
            List<EssentialOil> oils,
            int totalPercent) {

        int totalWeight = oils.stream()
                .mapToInt(EssentialOil::getPower)
                .sum();

        for (EssentialOil oil : oils) {

            int calculated = (oil.getPower() * totalPercent) / totalWeight;

            int finalPercent = Math.min(calculated, oil.getMaxPercent());

            result.put(oil, finalPercent);
        }
    }

    // =========================
    // NORMALISATION FINALE
    // =========================
    private void normalize(Map<EssentialOil, Integer> result) {

        int total = result.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        int difference = 100 - total;

        if (difference == 0) {
            return;
        }

        // Corrige sur la première huile (déterministe)
        EssentialOil firstKey = result.keySet().iterator().next();
        result.put(firstKey, result.get(firstKey) + difference);
    }

}
