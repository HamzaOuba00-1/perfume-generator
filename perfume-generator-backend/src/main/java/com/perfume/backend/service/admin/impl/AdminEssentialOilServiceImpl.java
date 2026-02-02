package com.perfume.backend.service.admin.impl;

import com.perfume.backend.domain.model.EssentialOil;
import com.perfume.backend.dto.admin.CreateEssentialOilRequest;
import com.perfume.backend.dto.response.EssentialOilDto;
import com.perfume.backend.exception.BusinessException;
import com.perfume.backend.mapper.EssentialOilMapper;
import com.perfume.backend.repository.EssentialOilRepository;
import com.perfume.backend.service.admin.AdminEssentialOilService;
import com.perfume.backend.service.admin.AdminImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminEssentialOilServiceImpl implements AdminEssentialOilService {

    private final EssentialOilRepository essentialOilRepository;
    private final AdminImageStorageService storageService;

    public AdminEssentialOilServiceImpl(
            EssentialOilRepository essentialOilRepository,
            AdminImageStorageService storageService
    ) {
        this.essentialOilRepository = essentialOilRepository;
        this.storageService = storageService;
    }

    private String normalizeName(String s) {
        if (s == null) return null;
        return s.trim().replaceAll("\\s{2,}", " ");
    }

    @Override
    public EssentialOilDto createOil(CreateEssentialOilRequest request) {
        String name = normalizeName(request.getName());
        String imageUrl = request.getImageUrl() == null ? null : request.getImageUrl().trim();

        if (imageUrl == null || imageUrl.isBlank()) {
            throw new BusinessException("L'image est obligatoire.");
        }

        if (!storageService.oilImageExists(imageUrl)) {
            throw new BusinessException("Image introuvable. Veuillez uploader l'image.");
        }

        essentialOilRepository.findByNameIgnoreCase(name)
                .ifPresent(o -> {
                    throw new BusinessException("Une huile essentielle avec ce nom existe déjà.");
                });

        EssentialOil oil = new EssentialOil(
                name,
                request.getNoteType(),
                request.getPower(),
                request.getMaxPercent(),
                imageUrl
        );

        return EssentialOilMapper.toDto(essentialOilRepository.save(oil));
    }

    @Override
    public EssentialOilDto updateOil(Long id, CreateEssentialOilRequest request) {
        EssentialOil oil = essentialOilRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Huile essentielle introuvable."));

        String name = normalizeName(request.getName());
        String imageUrl = request.getImageUrl() == null ? null : request.getImageUrl().trim();

        if (imageUrl == null || imageUrl.isBlank()) {
            throw new BusinessException("L'image est obligatoire.");
        }

        if (!storageService.oilImageExists(imageUrl)) {
            throw new BusinessException("Image introuvable. Veuillez uploader l'image.");
        }

        essentialOilRepository.findByNameIgnoreCase(name)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessException("Une autre huile essentielle porte déjà ce nom.");
                });

        oil.setName(name);
        oil.setNoteType(request.getNoteType());
        oil.setPower(request.getPower());
        oil.setMaxPercent(request.getMaxPercent());
        oil.setImageUrl(imageUrl);

        return EssentialOilMapper.toDto(essentialOilRepository.save(oil));
    }

    @Override
    public void deleteOil(Long id) {
        EssentialOil oil = essentialOilRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Huile essentielle introuvable."));
        essentialOilRepository.delete(oil);
    }
}
