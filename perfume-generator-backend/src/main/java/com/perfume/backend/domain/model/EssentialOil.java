package com.perfume.backend.domain.model;

import jakarta.persistence.*;

/**
 * Entité représentant une huile essentielle utilisée
 * comme matière première en parfumerie.
 */
@Entity
@Table(name = "essential_oil")
public class EssentialOil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom unique de l'huile essentielle.
     * Exemple : Citron, Lavande, Patchouli
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Position dans la pyramide olfactive :
     * TETE, COEUR ou FOND
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoteType noteType;

    /**
     * Puissance olfactive :
     * 1 = forte, 2 = moyenne, 3 = faible
     */
    @Column(nullable = false)
    private int power;

    /**
     * Pourcentage maximal autorisé dans un parfum.
     * Sert à limiter les matières dominantes.
     */
    @Column(nullable = false)
    private int maxPercent;

    // =====================
    // Constructeurs
    // =====================


    /**
     * URL ou chemin de l'image de l'huile essentielle
     */
    @Column(nullable = false)
    private String imageUrl;

    protected EssentialOil() {
        // requis par JPA
    }

    public EssentialOil(String name, NoteType noteType, int power, int maxPercent, String imageUrl) {
        this.name = name;
        this.noteType = noteType;
        this.power = power;
        this.maxPercent = maxPercent;
        this.imageUrl = imageUrl;
    }

    // =====================
    // Getters uniquement
    // =====================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public int getPower() {
        return power;
    }

    public int getMaxPercent() {
        return maxPercent;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
}
