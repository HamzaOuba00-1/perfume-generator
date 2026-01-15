package com.perfume.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PerfumeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("null")
    @Test
    void shouldGeneratePerfumeSuccessfully() throws Exception {

        String json = """
            {
              "oils": ["Citron", "Bergamote", "Lavande", "Patchouli", "Vanille"]
            }
            """;

        mockMvc.perform(post("/api/perfumes/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.composition").isArray())
                .andExpect(jsonPath("$.composition.length()").value(5));
    }

    @SuppressWarnings("null")
    @Test
    void shouldReturnBusinessErrorWhenInvalidStructure() throws Exception {

        String json = """
            {
              "oils": ["Citron", "Lavande"]
            }
            """;

        mockMvc.perform(post("/api/perfumes/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BUSINESS_ERROR"));
    }
}
