package com.perfume.backend.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfume.backend.dto.admin.CreateEssentialOilRequest;
import com.perfume.backend.domain.model.NoteType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests d'intégration de l'API admin.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AdminEssentialOilIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // =========================
    // SECURITY
    // =========================

    @Test
    void shouldReturn401_whenNoAuthentication() throws Exception {
        mockMvc.perform(post("/api/admin/oils"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn401_whenBadCredentials() throws Exception {
        mockMvc.perform(post("/api/admin/oils")
                .with(httpBasic("admin", "wrongPassword")))
                .andExpect(status().isUnauthorized());
    }

    // =========================
    // CREATE
    // =========================

    @Test
    void shouldCreateOil_whenAdminAuthenticated() throws Exception {

        CreateEssentialOilRequest request = validRequest("Santal");

        mockMvc.perform(post("/api/admin/oils")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.noteType").value("BASE"))
                .andExpect(jsonPath("$.imageUrl").value("test.png"));
    }

    @Test
    void shouldReturn400_whenInvalidRequest() throws Exception {

        CreateEssentialOilRequest invalid = new CreateEssentialOilRequest();
        invalid.setName(""); // invalide

        mockMvc.perform(post("/api/admin/oils")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    void shouldUpdateOil_whenAdminAuthenticated() throws Exception {

        // 1️⃣ Création
        CreateEssentialOilRequest create = validRequest("Encens");

        String response = mockMvc.perform(post("/api/admin/oils")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(create)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        // 2️⃣ Mise à jour
        CreateEssentialOilRequest update = validRequest("Encens Noir");
        update.setNoteType(NoteType.HEART);

        mockMvc.perform(put("/api/admin/oils/{id}", id)
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(update.getName()))
                .andExpect(jsonPath("$.noteType").value("HEART"));
    }

    // =========================
    // DELETE
    // =========================

    @Test
    void shouldDeleteOil_whenAdminAuthenticated() throws Exception {

        // Création préalable
        CreateEssentialOilRequest request = validRequest("Musc");

        String response = mockMvc.perform(post("/api/admin/oils")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        // Suppression
        mockMvc.perform(delete("/api/admin/oils/{id}", id)
                .with(httpBasic("admin", "admin123")))
                .andExpect(status().isNoContent());
    }

    // =========================
    // Utils
    // =========================

    private CreateEssentialOilRequest validRequest(String baseName) {
        CreateEssentialOilRequest r = new CreateEssentialOilRequest();
        r.setName(baseName + "-" + System.currentTimeMillis()); // ✅ unique
        r.setNoteType(NoteType.BASE);
        r.setPower(2);
        r.setMaxPercent(25);
        r.setImageUrl("test.png");
        return r;
    }
}
