package io.project.wolfgym.controller;

import io.project.wolfgym.dto.exercise.ExerciseCreateDTO;
import io.project.wolfgym.dto.exercise.ExerciseDTO;
import io.project.wolfgym.model.MuscleGroup;
import io.project.wolfgym.service.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ExerciseControllerTest {


    private MockMvc mockMvc;

    @Mock
    private ExerciseService exerciseService;

    private final String validExercise = """
            {
              "name": "Жим лежа",
              "description": "Базовое упражнение для грудных мышц", 
              "muscleGroup": "CHEST",
              "videoUrl": "https://www.youtube.com/watch?v=example1"
            }""";

    private final String invalidExercise = """
            {
              "name": "Жим лежа", 
              "description": "Базовое упражнение для грудных мышц",
              "muscleGroup": "CHESTs",
              "videoUrl": "https://www.youtube.com/watch?v=example1"
            }""";

    @BeforeEach
    void setUp() {
        // Создаем контроллер вручную и инжектим моки
        ExerciseController controller = new ExerciseController(exerciseService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createExercise_WithValidData_ReturnsOk() throws Exception {
        // Given
        ExerciseDTO responseDTO = new ExerciseDTO();
        responseDTO.setName("Жим лежа");
        responseDTO.setMuscleGroup(MuscleGroup.CHEST);
        responseDTO.setDescription("Базовое упражнение для грудных мышц");
        responseDTO.setVideoUrl("https://www.youtube.com/watch?v=example1");

        when(exerciseService.create(any(ExerciseCreateDTO.class))).thenReturn(responseDTO);

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
                .andExpect(status().isBadRequest());
    }

    @Test
    void showExercise_ReturnsOk() throws Exception {
        //Given
        var exercise = new ExerciseDTO();
        exercise.setName("Жим ногами");
        exercise.setDescription("5 подходов по 10 раз");
        exercise.setMuscleGroup(MuscleGroup.LEGS);
        exercise.setCreatedAt(LocalDateTime.now());

        //When
        when(exerciseService.show(1L)).thenReturn(exercise);

        //Then
        mockMvc.perform(get("/api/exercises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Жим ногами"))
                .andExpect(jsonPath("$.muscleGroup").value("LEGS"))
                .andExpect(jsonPath("$.description").value("5 подходов по 10 раз"));
    }

    @Test
    void showAllExercises_ReturnsOk() throws Exception {
        //Given
        var exercise = new ExerciseDTO();
        exercise.setName("Жим ногами");
        exercise.setDescription("5 подходов по 10 раз");
        exercise.setMuscleGroup(MuscleGroup.LEGS);
        exercise.setCreatedAt(LocalDateTime.now());

        var exercise2 = new ExerciseDTO();
        exercise2.setName("Жим гантелей в наклоне");
        exercise2.setDescription("5 подходов по 12 раз");
        exercise2.setMuscleGroup(MuscleGroup.CHEST);
        exercise2.setCreatedAt(LocalDateTime.now());

        List<ExerciseDTO> exerciseDTOList = Arrays.asList(exercise, exercise2);

        //When
        when(exerciseService.showAll()).thenReturn(exerciseDTOList);

        //Then
        mockMvc.perform(get("/api/exercises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // Проверяем количество элементов
                .andExpect(jsonPath("$[0].name").value("Жим ногами"))
                .andExpect(jsonPath("$[0].muscleGroup").value("LEGS"))
                .andExpect(jsonPath("$[0].description").value("5 подходов по 10 раз"))
                .andExpect(jsonPath("$[1].name").value("Жим гантелей в наклоне"))
                .andExpect(jsonPath("$[1].muscleGroup").value("CHEST"))
                .andExpect(jsonPath("$[1].description").value("5 подходов по 12 раз"));
    }
}