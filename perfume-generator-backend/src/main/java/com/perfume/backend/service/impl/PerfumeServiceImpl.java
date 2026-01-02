package com.perfume.backend.service.impl;

import com.perfume.backend.domain.engine.PerfumeGenerationEngine;
import com.perfume.backend.domain.model.EssentialOil;
import com.perfume.backend.domain.model.PerfumeRecipe;
import com.perfume.backend.exception.BusinessException;
import com.perfume.backend.repository.EssentialOilRepository;
import com.perfume.backend.service.PerfumeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du service de génération de parfum.
 */
@Service
public class PerfumeServiceImpl implements PerfumeService {

    private final EssentialOilRepository essentialOilRepository;
    private final PerfumeGenerationEngine perfumeGenerationEngine;

    public PerfumeServiceImpl(EssentialOilRepository essentialOilRepository,
                              PerfumeGenerationEngine perfumeGenerationEngine) {
        this.essentialOilRepository = essentialOilRepository;
        this.perfumeGenerationEngine = perfumeGenerationEngine;
    }

    @Override
    public PerfumeRecipe generatePerfume(List<String> oilNames) {

        // 1️⃣ Validation simple
        if (oilNames == null || oilNames.isEmpty()) {
            throw new BusinessException("La liste des huiles ne peut pas être vide.");
        }

        // 2️⃣ Récupération des huiles depuis la base
        List<EssentialOil> oils = new ArrayList<>();

        for (String name : oilNames) {
            EssentialOil oil = essentialOilRepository
                    .findByNameIgnoreCase(name)
                    .orElseThrow(() ->
                        new BusinessException("Huile essentielle inconnue : " + name)
                    );

            oils.add(oil);
        }

        // 3️⃣ Appel du moteur métier
        return perfumeGenerationEngine.generate(oils);
    }
}
