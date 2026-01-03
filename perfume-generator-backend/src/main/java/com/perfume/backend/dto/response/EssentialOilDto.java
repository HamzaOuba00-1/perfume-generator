package com.perfume.backend.dto.response;

import com.perfume.backend.domain.model.NoteType;

/**
 * DTO exposé au frontend pour une huile essentielle.
 */
public class EssentialOilDto {

    private Long id;
    private String name;
    private NoteType noteType;
    private int power;
    private int maxPercent;

    public EssentialOilDto(Long id,
                           String name,
                           NoteType noteType,
                           int power,
                           int maxPercent) {
        this.id = id;
        this.name = name;
        this.noteType = noteType;
        this.power = power;
        this.maxPercent = maxPercent;
    }

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
}
