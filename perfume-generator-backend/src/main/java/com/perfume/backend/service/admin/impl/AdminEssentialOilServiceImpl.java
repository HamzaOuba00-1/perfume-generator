package com.perfume.backend.service.admin.impl;

import com.perfume.backend.domain.model.EssentialOil;
import com.perfume.backend.dto.admin.CreateEssentialOilRequest;
import com.perfume.backend.dto.response.EssentialOilDto;
import com.perfume.backend.exception.BusinessException;
import com.perfume.backend.mapper.EssentialOilMapper;
import com.perfume.backend.repository.EssentialOilRepository;
import com.perfume.backend.service.admin.AdminEssentialOilService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation du service admin pour la gestion
 * des huiles essentielles.
 */
@Service
@Transactional
public class AdminEssentialOilServiceImpl implements AdminEssentialOilService {

    private final EssentialOilRepository essentialOilRepository;

    public AdminEssentialOilServiceImpl(EssentialOilRepository essentialOilRepository) {
        this.essentialOilRepository = essentialOilRepository;
    }

    /**
     * ➕ Création d'une huile essentielle
     */
    @Override
    public EssentialOilDto createOil(CreateEssentialOilRequest request) {

        // 🔒 Vérifie unicité du nom
        essentialOilRepository.findByNameIgnoreCase(request.getName())
                .ifPresent(oil -> {
                    throw new BusinessException(
                            "Une huile essentielle avec ce nom existe déjà."
                    );
                });

        EssentialOil oil = new EssentialOil(
                request.getName(),
                request.getNoteType(),
                request.getPower(),
                request.getMaxPercent(),
                request.getImageUrl()
        );

        EssentialOil saved = essentialOilRepository.save(oil);

        return EssentialOilMapper.toDto(saved);
    }

    /**
     * ✏️ Mise à jour d'une huile essentielle
     */
    @Override
    public EssentialOilDto updateOil(Long id, CreateEssentialOilRequest request) {

        EssentialOil oil = essentialOilRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Huile essentielle introuvable.")
                );

        // 🔒 Vérifie unicité du nom (si changé)
        essentialOilRepository.findByNameIgnoreCase(request.getName())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessException(
                            "Une autre huile essentielle porte déjà ce nom."
                    );
                });

        // Mise à jour contrôlée
        oil.setName(request.getName());
        oil.setNoteType(request.getNoteType());
        oil.setPower(request.getPower());
        oil.setMaxPercent(request.getMaxPercent());
        oil.setImageUrl(request.getImageUrl());

        EssentialOil updated = essentialOilRepository.save(oil);

        return EssentialOilMapper.toDto(updated);
    }

    /**
     * 🗑️ Suppression d'une huile essentielle
     */
    @SuppressWarnings("null")
@Override
    public void deleteOil(Long id) {

        EssentialOil oil = essentialOilRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Huile essentielle introuvable.")
                );

        essentialOilRepository.delete(oil);
    }
}
