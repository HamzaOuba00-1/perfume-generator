package com.perfume.backend.service.admin;

import com.perfume.backend.domain.model.EssentialOil;
import com.perfume.backend.domain.model.NoteType;
import com.perfume.backend.dto.admin.CreateEssentialOilRequest;
import com.perfume.backend.exception.BusinessException;
import com.perfume.backend.repository.EssentialOilRepository;
import com.perfume.backend.service.admin.impl.AdminEssentialOilServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminEssentialOilServiceImplTest {

    @Mock
    private EssentialOilRepository essentialOilRepository;

    @InjectMocks
    private AdminEssentialOilServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // =========================
    // CREATE
    // =========================

    @SuppressWarnings("null")
    @Test
    void createOil_shouldCreateOil_whenValidRequest() {
        CreateEssentialOilRequest request = buildRequest("Citron");

        when(essentialOilRepository.findByNameIgnoreCase("Citron"))
                .thenReturn(Optional.empty());

        when(essentialOilRepository.save(any(EssentialOil.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.createOil(request);

        assertNotNull(result);
        assertEquals("Citron", result.getName());
        verify(essentialOilRepository).save(any(EssentialOil.class));
    }

    @SuppressWarnings("null")
    @Test
    void createOil_shouldThrowException_whenNameAlreadyExists() {
        CreateEssentialOilRequest request = buildRequest("Citron");

        when(essentialOilRepository.findByNameIgnoreCase("Citron"))
                .thenReturn(Optional.of(mock(EssentialOil.class)));

        assertThrows(BusinessException.class,
                () -> service.createOil(request));

        verify(essentialOilRepository, never()).save(any());
    }

    // =========================
    // UPDATE
    // =========================

    @SuppressWarnings("null")
    @Test
    void updateOil_shouldUpdateOil_whenValid() {
        CreateEssentialOilRequest request = buildRequest("Lavande");

        EssentialOil existing = new EssentialOil(
                "AncienNom", NoteType.HEART, 2, 30, "old.png"
        );

        when(essentialOilRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(essentialOilRepository.findByNameIgnoreCase("Lavande"))
                .thenReturn(Optional.empty());

        when(essentialOilRepository.save(any(EssentialOil.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.updateOil(1L, request);

        assertEquals("Lavande", result.getName());
        verify(essentialOilRepository).save(existing);
    }

    @Test
    void updateOil_shouldThrowException_whenOilNotFound() {
        when(essentialOilRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(BusinessException.class,
                () -> service.updateOil(99L, buildRequest("Test")));
    }

    // =========================
    // DELETE
    // =========================

    @Test
    void deleteOil_shouldDelete_whenExists() {
        EssentialOil oil = new EssentialOil(
                "Patchouli", NoteType.BASE, 1, 20, "patchouli.png"
        );

        when(essentialOilRepository.findById(1L))
                .thenReturn(Optional.of(oil));

        service.deleteOil(1L);

        verify(essentialOilRepository).delete(oil);
    }

    @Test
    void deleteOil_shouldThrowException_whenNotFound() {
        when(essentialOilRepository.findById(42L))
                .thenReturn(Optional.empty());

        assertThrows(BusinessException.class,
                () -> service.deleteOil(42L));
    }

    // =========================
    // Utils
    // =========================

    private CreateEssentialOilRequest buildRequest(String name) {
        CreateEssentialOilRequest request = new CreateEssentialOilRequest();
        request.setName(name);
        request.setNoteType(NoteType.TOP);
        request.setPower(2);
        request.setMaxPercent(30);
        request.setImageUrl("image.png");
        return request;
    }
}
