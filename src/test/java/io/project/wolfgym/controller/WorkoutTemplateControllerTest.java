package io.project.wolfgym.controller;

import io.project.wolfgym.dto.exercise.ExerciseDTO;
import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateCreateDTO;
import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateDTO;
import io.project.wolfgym.model.MuscleGroup;
import io.project.wolfgym.service.WorkoutTemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
class WorkoutTemplateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WorkoutTemplateService service;

    private ExerciseDTO exercise;
    private ExerciseDTO exercise2;
    private List<ExerciseDTO> exerciseDTOList;
    private WorkoutTemplateDTO workoutTemplateDTO;

    @BeforeEach
    void setUp() {
        // Создаем контроллер вручную и инжектим моки
        WorkoutTemplateController controller = new WorkoutTemplateController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        // Создаем базовые два упражнения
        exercise = new ExerciseDTO();
        exercise.setName("Жим ногами");
        exercise.setDescription("5 подходов по 10 раз");
        exercise.setMuscleGroup(MuscleGroup.LEGS);
        exercise.setCreatedAt(LocalDateTime.now());

        exercise2 = new ExerciseDTO();
        exercise2.setName("Жим гантелей в наклоне");
        exercise2.setDescription("5 подходов по 12 раз");
        exercise2.setMuscleGroup(MuscleGroup.CHEST);
        exercise2.setCreatedAt(LocalDateTime.now());
        // Создаем базовый список упражнений
        exerciseDTOList = Arrays.asList(exercise, exercise2);
        // Создаём базовый шаблон
        workoutTemplateDTO = new WorkoutTemplateDTO();
        workoutTemplateDTO.setName("Спина + Бицепс");
        workoutTemplateDTO.setDescription("Силовая тренировка для верхней части тела");
        workoutTemplateDTO.setExercises(exerciseDTOList);
    }

    private final String validRequest = """
            {
              "name": "Спина + Бицепс",
              "description": "Силовая тренировка для верхней части тела",
              "exercisesIds": [1, 2]
            }
            """;
    private final String invalidRequest = """
            {
              "name": null,
              "description": "Силовая тренировка для верхней части тела",
              "exercisesIds": [1]
            }
            """;

    @Test
    void createWorkoutTemplate_withValidFields_ReturnsOk() throws Exception {
        //When
        when(service.create(any(WorkoutTemplateCreateDTO.class))).thenReturn(workoutTemplateDTO);
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
                .andExpect(jsonPath("$.exercises[0].name").value("Жим ногами"))
                .andExpect(jsonPath("$.exercises[1].name").value("Жим гантелей в наклоне"));

    }

    @Test
    void createWorkoutTemplate_withInvalidFields_ReturnsBadRequest() throws Exception {
        //then
        mockMvc.perform(post("/api/workout-templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());

    }

    @Test
    void showWorkoutTemplateById_ReturnsOk() throws Exception {

        when(service.show(1L)).thenReturn(workoutTemplateDTO);

        mockMvc.perform(get("/api/workout-templates/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Спина + Бицепс"))
                .andExpect(jsonPath("$.description")
                        .value("Силовая тренировка для верхней части тела"))
                .andExpect(jsonPath("$.exercises").isArray())
                .andExpect(jsonPath("$.exercises[0].name").value("Жим ногами"))
                .andExpect(jsonPath("$.exercises[0].description")
                        .value("5 подходов по 10 раз"))
                .andExpect(jsonPath("$.exercises[1].name").value("Жим гантелей в наклоне"))
                .andExpect(jsonPath("$.exercises[1].description").
                        value("5 подходов по 12 раз"));

    }

    @Test
    void showAllWorkoutTemplates_ReturnsOk() throws Exception {
        //Given
        var workoutTemplateDTO2 = new WorkoutTemplateDTO();
        var exercisesList = new ArrayList<ExerciseDTO>();
        exercisesList.add(exercise);
        workoutTemplateDTO2.setName("Трицепс");
        workoutTemplateDTO2.setDescription("Упражнения на трицепс");
        workoutTemplateDTO2.setExercises(exercisesList);
        var wrktmpl = Arrays.asList(workoutTemplateDTO, workoutTemplateDTO2);
        //When
        when(service.showAll()).thenReturn(wrktmpl);
        //Then
        mockMvc.perform(get("/api/workout-templates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Спина + Бицепс"))
                .andExpect(jsonPath("$[0].exercises").isArray())
                .andExpect(jsonPath("$[1].name").value("Трицепс"))
                .andExpect(jsonPath("$[1].description").value("Упражнения на трицепс"))
                .andExpect(jsonPath("$[1].exercises").isArray())
                .andExpect(jsonPath("$[1].exercises").isNotEmpty());
    }

}