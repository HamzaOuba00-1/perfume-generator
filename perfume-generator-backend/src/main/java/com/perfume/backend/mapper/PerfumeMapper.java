package com.perfume.backend.mapper;

import com.perfume.backend.domain.model.EssentialOil;
import com.perfume.backend.domain.model.PerfumeRecipe;
import com.perfume.backend.dto.response.OilPercentageDto;
import com.perfume.backend.dto.response.PerfumeResponseDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir les objets du domaine
 * en DTOs exposés par l'API.
 */
public final class PerfumeMapper {

    // Empêche l'instanciation
    private PerfumeMapper() {
    }

    /**
     * Convertit une recette de parfum (domaine)
     * en DTO de réponse.
     */
    public static PerfumeResponseDto toDto(PerfumeRecipe recipe) {

        List<OilPercentageDto> composition = recipe.getComposition()
                .entrySet()
                .stream()
                .map(PerfumeMapper::toOilPercentageDto)
                .collect(Collectors.toList());

        return new PerfumeResponseDto(composition);
    }

    private static OilPercentageDto toOilPercentageDto(
            Map.Entry<EssentialOil, Integer> entry) {

        return new OilPercentageDto(
                entry.getKey().getName(),
                entry.getValue()
        );
    }
}
