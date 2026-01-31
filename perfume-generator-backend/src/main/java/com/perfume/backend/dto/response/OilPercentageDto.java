package com.perfume.backend.dto.response;

/**
 * DTO représentant une huile essentielle
 * et son pourcentage dans le parfum.
 */
public class OilPercentageDto {

    private String oilName;
    private int percentage;
    private String imageUrl;

    public OilPercentageDto(String oilName, int percentage, String imageUrl) {
        this.oilName = oilName;
        this.percentage = percentage;
        this.imageUrl = imageUrl;

    }

    public String getOilName() {
        return oilName;
    }

    public int getPercentage() {
        return percentage;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
