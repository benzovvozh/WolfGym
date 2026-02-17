package io.project.wolfgym.controller;

import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ExerciseControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    private final String validExercise = """
            {
              "name": "Жим лежа",
              "description": "Базовое упражнение для грудных мышц", 
              "muscleGroup": "CHEST",
              "videoUrl": "https://www.youtube.com/watch?v=example1",
              "createdBy": "admin"
            }""";

    private final String invalidExercise = """
            {
              "name": "", 
              "description": "Базовое упражнение для грудных мышц",
              "muscleGroup": "CHESTs",
              "videoUrl": "https://www.youtube.com/watch?v=example1",
              "createdBy": ""
            }""";


    @Test
    void createExercise_WithValidData_ReturnsOk() throws Exception {

        mockMvc.perform(post("/api/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validExercise))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Жим лежа"))
                .andExpect(jsonPath("$.muscleGroup").value("CHEST"))
                .andExpect(jsonPath("$.description")
                        .value("Базовое упражнение для грудных мышц"));

    }

    @Test
    void createExercise_WithInvalidMuscleGroup_ReturnsBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidExercise))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createExercise_WithMissingName_ReturnsBadRequest() throws Exception {
        String invalidExerciseWithMissingName = """
                {
                  "description": "Базовое упражнение для грудных мышц",
                  "muscleGroup": "CHEST"
                }""";
        mockMvc.perform(post("/api/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidExerciseWithMissingName))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Ошибка валидации"))
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    @SneakyThrows
    void createExercise_WithInvalidName_ReturnBadRequest() {
        String exerciseWithBlankName = """
                {
                "name": "",
                "muscleGroup": "CHEST",
                "createdBy": "admin"
                }
                """;
        mockMvc.perform(post("/api/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(exerciseWithBlankName))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Ошибка валидации"))
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors.name")
                        .value("Название упражнения не может быть пустым"));
    }

    @Test
    @SneakyThrows
    void createExercise_WithInvalidCreatedBy_ReturnsBadRequest() {
        String exerciseWithBlankCreatedBy = """
                {
                "name": "exercise",
                "muscleGroup": "CHEST",
                "createdBy": ""
                }
                """;
        mockMvc.perform(post("/api/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(exerciseWithBlankCreatedBy))
                .andExpect(jsonPath("$.message").value("Ошибка валидации"))
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors.createdBy")
                        .value("CreatedBy не может быть пустым"));
    }

    @Test
    void showExercise_ReturnsOk() throws Exception {
        mockMvc.perform(get("/api/exercises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Жим лёжа"))
                .andExpect(jsonPath("$.muscleGroup").value("CHEST"))
                .andExpect(jsonPath("$.description").value("4 подхода по 10 раз"));
    }

    @Test
    void showAllExercises_ReturnsOk() throws Exception {
        //Then
        mockMvc.perform(get("/api/exercises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // Проверяем количество элементов
                .andExpect(jsonPath("$[0].name").value("Жим лёжа"))
                .andExpect(jsonPath("$[0].muscleGroup").value("CHEST"))
                .andExpect(jsonPath("$[0].description").value("4 подхода по 10 раз"))
                .andExpect(jsonPath("$[1].name").value("Жим Арнольда"))
                .andExpect(jsonPath("$[1].muscleGroup").value("SHOULDERS"))
                .andExpect(jsonPath("$[1].description").value("Поднятие гантелей над головой"));
    }

    @Test
    void deleteExercise_ReturnsNoContent() throws Exception {
        String newExercise = """
                {
                  "name": "Упражнение для удаления",
                  "description": "Это упражнение будет удалено",
                  "muscleGroup": "CHEST",
                  "createdBy": "admin"
                }""";
        String response = mockMvc.perform(post("/api/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newExercise))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Long exerciseId = JsonPath.parse(response).read("$.id", Long.class);

        mockMvc.perform(delete("/api/exercises/" + exerciseId))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/exercises/" + exerciseId))
                .andExpect(status().isNotFound());
    }

}