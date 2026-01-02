package com.perfume.backend.dto.response;

/**
 * DTO représentant une huile essentielle
 * et son pourcentage dans le parfum.
 */
public class OilPercentageDto {

    private String oilName;
    private int percentage;

    public OilPercentageDto(String oilName, int percentage) {
        this.oilName = oilName;
        this.percentage = percentage;
    }

    public String getOilName() {
        return oilName;
    }

    public int getPercentage() {
        return percentage;
    }
}
