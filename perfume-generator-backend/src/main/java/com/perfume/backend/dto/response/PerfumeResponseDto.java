package com.perfume.backend.dto.response;

import java.util.List;

/**
 * DTO de réponse pour une recette de parfum générée.
 */
public class PerfumeResponseDto {

    private List<OilPercentageDto> composition;

    public PerfumeResponseDto(List<OilPercentageDto> composition) {
        this.composition = composition;
    }

    public List<OilPercentageDto> getComposition() {
        return composition;
    }
}
