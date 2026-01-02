package com.perfume.backend.controller;

import com.perfume.backend.dto.request.PerfumeRequestDto;
import com.perfume.backend.dto.response.PerfumeResponseDto;
import com.perfume.backend.mapper.PerfumeMapper;
import com.perfume.backend.service.PerfumeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST pour la génération de parfums.
 */
@RestController
@RequestMapping("/api/perfumes")
public class PerfumeController {

    private final PerfumeService perfumeService;

    public PerfumeController(PerfumeService perfumeService) {
        this.perfumeService = perfumeService;
    }

    /**
     * Génère une recette de parfum à partir
     * des huiles essentielles choisies par l'utilisateur.
     */
    @PostMapping("/generate")
    public PerfumeResponseDto generatePerfume(
            @RequestBody @Valid PerfumeRequestDto request) {

        return PerfumeMapper.toDto(
                perfumeService.generatePerfume(request.getOils())
        );
    }
}
