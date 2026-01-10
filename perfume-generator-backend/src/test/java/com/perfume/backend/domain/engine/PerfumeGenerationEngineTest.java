package com.perfume.backend.domain.engine;

import com.perfume.backend.domain.model.EssentialOil;
import com.perfume.backend.domain.model.NoteType;
import com.perfume.backend.domain.model.PerfumeRecipe;
import com.perfume.backend.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PerfumeGenerationEngineTest {

    private PerfumeGenerationEngine engine;

    @BeforeEach
    void setUp() {
        engine = new PerfumeGenerationEngine();
    }

    @Test
    void shouldGenerateValidPerfumeRecipe() {

        List<EssentialOil> oils = List.of(
                new EssentialOil("Citron", NoteType.TETE, 3, 30, "citron.png"),
                new EssentialOil("Lavande", NoteType.COEUR, 2, 35, "lavande.png"),
                new EssentialOil("Patchouli", NoteType.FOND, 1, 20, "patchouli.png")
        );

        PerfumeRecipe recipe = engine.generate(oils);

        assertNotNull(recipe);
        assertEquals(100, recipe.getTotalPercentage());
        assertEquals(3, recipe.getComposition().size());
    }

    @Test
    void shouldThrowExceptionWhenMissingNoteType() {

        List<EssentialOil> oils = List.of(
                new EssentialOil("Citron", NoteType.TETE, 3, 30, "citron.png"),
                new EssentialOil("Lavande", NoteType.COEUR, 2, 35, "lavande.png")
        );

        assertThrows(BusinessException.class,
                () -> engine.generate(oils));
    }
}
