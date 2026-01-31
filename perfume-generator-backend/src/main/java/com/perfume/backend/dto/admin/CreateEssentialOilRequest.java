package com.perfume.backend.dto.admin;

import com.perfume.backend.domain.model.NoteType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

/**
 * DTO utilisé par l'admin pour créer ou modifier
 * une huile essentielle.
 */
public class CreateEssentialOilRequest {

    @NotBlank(message = "Le nom de l'huile est obligatoire.")
    @Size(min = 2, max = 50, message = "Le nom doit faire entre 2 et 50 caractères.")
    @Pattern(regexp = "^[\\p{L}][\\p{L}\\p{M} '\\-]{1,49}$", message = "Nom invalide (lettres, espaces, tirets et apostrophes uniquement).")
    private String name;

    @NotNull(message = "Le type de note est obligatoire.")
    private NoteType noteType;

    /**
     * Puissance olfactive :
     * 1 = forte, 2 = moyenne, 3 = faible
     */
    @Min(value = 1, message = "La puissance minimale est 1 (forte).")
    @Max(value = 3, message = "La puissance maximale est 3 (faible).")
    private int power;

    @Min(value = 1, message = "Le pourcentage maximal doit être supérieur à 0.")
    @Max(value = 100, message = "Le pourcentage maximal ne peut pas dépasser 100.")
    private int maxPercent;

    @NotBlank(message = "L'image est obligatoire.")
    @Size(max = 80, message = "Nom de fichier image trop long.")
    @Pattern(regexp = "^[a-f0-9\\-]{36}\\.(png|jpg|webp)$", message = "Nom de fichier image invalide.")
    private String imageUrl;

    // =====================
    // Getters / Setters
    // =====================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getMaxPercent() {
        return maxPercent;
    }

    public void setMaxPercent(int maxPercent) {
        this.maxPercent = maxPercent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
