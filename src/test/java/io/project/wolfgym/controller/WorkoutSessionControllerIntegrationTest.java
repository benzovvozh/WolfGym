package io.project.wolfgym.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class WorkoutSessionControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    private final String validSession = """
            {
              "templateId": 1,
              "createdBy": "Admin"
            }""";

    // несуществующий идентификатор
    private final String invalidSession = """
            {
              "templateId": 10, 
              "createdBy": "Admin"
            }""";
    private final static String BASE_URL = "/api/sessions";

    @Test
    void createWorkoutSession_WithValidData_ReturnsOk() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validSession))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.createdBy").value("Admin"))
                .andExpect(jsonPath("$.template.name").value("Грудные мышцы"))
                .andExpect(jsonPath("$.template.description")
                        .value("Тренировка грудной группы мышц"));
    }

    @Test
    void createWorkoutSession_WithInvalidData_ReturnsNotFound() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidSession))
                .andExpect(status().isNotFound());
    }

    @Test
    void showWorkoutSession_ReturnsOk() throws Exception {
        mockMvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.template.name").value("Грудные мышцы"))
                .andExpect(jsonPath("$.createdBy").value("telegram_123456"))
                .andExpect(jsonPath("$.duration").value(45));

    }

    @Test
    void showWorkoutSession_ReturnsNotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/25"))
                .andExpect(status().isNotFound());
    }

    @Test
    void showAllWorkoutSessions_ReturnsOk() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].template.name").value("Грудные мышцы"))
                .andExpect(jsonPath("$[0].template.exercises[0].name").value("Жим лёжа"));
    }

}