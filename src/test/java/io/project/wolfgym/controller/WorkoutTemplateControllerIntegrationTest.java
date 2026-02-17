package io.project.wolfgym.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class WorkoutTemplateControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    private final String validRequest = """
            {
              "name": "Спина + Бицепс",
              "description": "Силовая тренировка для верхней части тела",
              "exercisesIds": [1, 2],
              "userId": "1"
            }
            """;
    private final String invalidRequest = """
            {
              "name": null,
              "description": "Силовая тренировка для верхней части тела",
              "exercisesIds": [1],
              "userId": "1"
            }
            """;

    @Test
    @Order(1)
    void createWorkoutTemplate_withInvalidFields_ReturnsBadRequest() throws Exception {
        //then
        mockMvc.perform(post("/api/workout-templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Ошибка валидации"))
                .andExpect(jsonPath("$.errors.name")
                        .value("Название тренировки не может быть пустым"));
    }

    @Test
    @Order(2)
    void showWorkoutTemplateById_ReturnsOk() throws Exception {

        mockMvc.perform(get("/api/workout-templates/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Грудные мышцы"))
                .andExpect(jsonPath("$.description")
                        .value("Тренировка грудной группы мышц"))
                .andExpect(jsonPath("$.exercises").isArray())
                .andExpect(jsonPath("$.exercises[0].name").value("Жим лёжа"))
                .andExpect(jsonPath("$.exercises[0].description")
                        .value("4 подхода по 10 раз"));

    }

    @Test
    @Order(3)
    void showAllWorkoutTemplates_ReturnsOk() throws Exception {

        mockMvc.perform(get("/api/workout-templates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Грудные мышцы"))
                .andExpect(jsonPath("$[0].description").value("Тренировка грудной группы мышц"))
                .andExpect(jsonPath("$[0].exercises").isArray())
                .andExpect(jsonPath("$[0].exercises").isNotEmpty())
                .andExpect(jsonPath("$[0].exercises[0].name").value("Жим лёжа"))
                .andExpect(jsonPath("$[1].name").value("Плечи"))
                .andExpect(jsonPath("$[1].description").value("Тренировка плеч"))
                .andExpect(jsonPath("$[1].exercises").isArray())
                .andExpect(jsonPath("$[1].exercises").isNotEmpty())
                .andExpect(jsonPath("$[1].exercises[0].name").value("Жим Арнольда"));
    }

    @Test
    @Order(4)
    void createWorkoutTemplate_withValidFields_ReturnsOk() throws Exception {
        //Then
        mockMvc.perform(post("/api/workout-templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Спина + Бицепс"))
                .andExpect(jsonPath("$.description")
                        .value("Силовая тренировка для верхней части тела"))
                .andExpect(jsonPath("$.exercises").isArray())
                .andExpect(jsonPath("$.exercises.length()").value(2))
                .andExpect(jsonPath("$.exercises[0].name").value("Жим лёжа"))
                .andExpect(jsonPath("$.exercises[1].name").value("Жим Арнольда"));

    }

    @Test
    @Order(5)
    void deleteWorkoutTemplate_ReturnsNoContent() throws Exception {
        String newWT = """
                {
                  "name": "Для удаления",
                  "description": "Будет удалено",
                  "exercisesIds": [1, 2],
                  "userId": "1"
                }
                """;
        var response = mockMvc.perform(post("/api/workout-templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newWT))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Long templateId = JsonPath.parse(response).read("$.workoutTemplateId", Long.class);
        mockMvc.perform(delete("/api/workout-templates/" + templateId))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/workout-templates/" + templateId))
                .andExpect(status().isNotFound());
    }

}