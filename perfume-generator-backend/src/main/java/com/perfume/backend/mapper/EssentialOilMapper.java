package com.perfume.backend.mapper;

import com.perfume.backend.domain.model.EssentialOil;
import com.perfume.backend.dto.response.EssentialOilDto;

public final class EssentialOilMapper {

    private EssentialOilMapper() {}

    public static EssentialOilDto toDto(EssentialOil oil) {
        return new EssentialOilDto(
                oil.getId(),
                oil.getName(),
                oil.getNoteType(),
                oil.getPower(),
                oil.getMaxPercent()
        );
    }
}
