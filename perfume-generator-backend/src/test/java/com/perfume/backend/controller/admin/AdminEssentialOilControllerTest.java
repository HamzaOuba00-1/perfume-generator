package com.perfume.backend.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfume.backend.dto.admin.CreateEssentialOilRequest;
import com.perfume.backend.dto.response.EssentialOilDto;
import com.perfume.backend.domain.model.NoteType;
import com.perfume.backend.security.SecurityConfig;
import com.perfume.backend.service.admin.AdminEssentialOilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests Web MVC du controller admin.
 */
@WebMvcTest(AdminEssentialOilController.class)
@Import(SecurityConfig.class)
class AdminEssentialOilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminEssentialOilService adminEssentialOilService;

    // =========================
    // SECURITY
    // =========================

    @Test
    void shouldReturn401_whenNotAuthenticated() throws Exception {
        mockMvc.perform(post("/api/admin/oils"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn403_whenNotAdmin() throws Exception {
        mockMvc.perform(post("/api/admin/oils"))
                .andExpect(status().isForbidden());
    }

    // =========================
    // CREATE
    // =========================

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateOil_whenValidRequest() throws Exception {

        CreateEssentialOilRequest request = validRequest();

        EssentialOilDto response = new EssentialOilDto(
                1L,
                "Citron",
                NoteType.TOP,
                2,
                30,
                "citron.png"
        );

        when(adminEssentialOilService.createOil(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/admin/oils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Citron"))
                .andExpect(jsonPath("$.noteType").value("TOP"))
                .andExpect(jsonPath("$.imageUrl").value("citron.png"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn400_whenValidationFails() throws Exception {

        CreateEssentialOilRequest invalid = new CreateEssentialOilRequest();
        invalid.setName(""); // invalide

        mockMvc.perform(post("/api/admin/oils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // DELETE
    // =========================

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteOil_whenAdmin() throws Exception {

        mockMvc.perform(delete("/api/admin/oils/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateOil_whenAdmin() throws Exception {

        CreateEssentialOilRequest request = validRequest();

        EssentialOilDto response = new EssentialOilDto(
                1L,
                "Lavande",
                NoteType.HEART,
                2,
                35,
                "lavande.png"
        );

        when(adminEssentialOilService.updateOil(eq(1L), any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/admin/oils/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lavande"));
    }

    // =========================
    // Utils
    // =========================

    private CreateEssentialOilRequest validRequest() {
        CreateEssentialOilRequest r = new CreateEssentialOilRequest();
        r.setName("Citron");
        r.setNoteType(NoteType.TOP);
        r.setPower(2);
        r.setMaxPercent(30);
        r.setImageUrl("citron.png");
        return r;
    }
}
