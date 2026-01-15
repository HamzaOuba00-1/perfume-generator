package com.perfume.backend.service;

import com.perfume.backend.domain.engine.PerfumeGenerationEngine;
import com.perfume.backend.domain.model.EssentialOil;
import com.perfume.backend.domain.model.NoteType;
import com.perfume.backend.domain.model.PerfumeRecipe;
import com.perfume.backend.exception.BusinessException;
import com.perfume.backend.repository.EssentialOilRepository;
import com.perfume.backend.service.impl.PerfumeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PerfumeServiceImplTest {

    @Test
    void shouldGeneratePerfumeUsingRepositoryAndEngine() {

        EssentialOilRepository repository = Mockito.mock(EssentialOilRepository.class);
        PerfumeGenerationEngine engine = Mockito.mock(PerfumeGenerationEngine.class);

        PerfumeService service = new PerfumeServiceImpl(repository, engine);

        EssentialOil citron = new EssentialOil("Citron", NoteType.TOP, 3, 30, "citron.png");
        PerfumeRecipe recipe = new PerfumeRecipe(Map.of(citron, 100));

        Mockito.when(repository.findByNameIgnoreCase("Citron"))
                .thenReturn(Optional.of(citron));

        Mockito.when(engine.generate(List.of(citron)))
                .thenReturn(recipe);

        PerfumeRecipe result = service.generatePerfume(List.of("Citron"));

        assertEquals(100, result.getTotalPercentage());
    }

    @Test
    void shouldThrowExceptionWhenOilNotFound() {

        EssentialOilRepository repository = Mockito.mock(EssentialOilRepository.class);
        PerfumeGenerationEngine engine = Mockito.mock(PerfumeGenerationEngine.class);

        PerfumeService service = new PerfumeServiceImpl(repository, engine);

        Mockito.when(repository.findByNameIgnoreCase("Unknown"))
                .thenReturn(Optional.empty());

        assertThrows(BusinessException.class,
                () -> service.generatePerfume(List.of("Unknown")));
    }
}
