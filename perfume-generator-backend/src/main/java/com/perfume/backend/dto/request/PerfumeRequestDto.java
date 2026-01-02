package com.perfume.backend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * DTO de requête pour la génération d'un parfum.
 */
public class PerfumeRequestDto {

    @NotEmpty(message = "La liste des huiles ne peut pas être vide.")
    @Size(min = 2, max = 10, message = "Veuillez sélectionner entre 2 et 10 huiles.")
    private List<String> oils;

    public List<String> getOils() {
        return oils;
    }

    public void setOils(List<String> oils) {
        this.oils = oils;
    }
}
