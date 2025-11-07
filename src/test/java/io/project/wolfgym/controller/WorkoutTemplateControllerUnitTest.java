package io.project.wolfgym.controller;

import io.project.wolfgym.customException.ExerciseNotFoundException;
import io.project.wolfgym.customException.WorkoutTemplateNotFoundException;
import io.project.wolfgym.dto.exercise.ExerciseDTO;
import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateCreateDTO;
import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateDTO;
import io.project.wolfgym.model.MuscleGroup;
import io.project.wolfgym.service.WorkoutTemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkoutTemplateControllerUnitTest {

    @Mock
    WorkoutTemplateService service;

    @InjectMocks
    WorkoutTemplateController controller;

    private final static String EX_NAME = "Жим ногами";
    private final static String EX_DESC = "4 подхода по 10 раз";
    private final static String WT_NAME = "Грудь";
    private final static String WT_DESC = "Тренировка грудных мышц";

    private ExerciseDTO exerciseDTO;
    private ExerciseDTO exerciseDTO2;
    private WorkoutTemplateDTO workoutTemplateDTO;
    private List<ExerciseDTO> exercisesList;

    @BeforeEach
    void setUp() {
        exerciseDTO = new ExerciseDTO();
        this.exerciseDTO.setName(EX_NAME);
        this.exerciseDTO.setDescription(EX_DESC);
        this.exerciseDTO.setMuscleGroup(MuscleGroup.CHEST);

        exerciseDTO2 = new ExerciseDTO();
        this.exerciseDTO2.setName("Name");
        this.exerciseDTO2.setDescription("Desc");
        this.exerciseDTO2.setMuscleGroup(MuscleGroup.LEGS);
        exercisesList = Arrays.asList(exerciseDTO, exerciseDTO2);

        workoutTemplateDTO = new WorkoutTemplateDTO();
        this.workoutTemplateDTO.setName(WT_NAME);
        this.workoutTemplateDTO.setDescription(WT_DESC);
        this.workoutTemplateDTO.setExercises(exercisesList);
    }

    //WT - Workout template

    @Test
    void handleShowWTById_ReturnsOk() throws WorkoutTemplateNotFoundException {
        //when & then
        when(service.show(1L)).thenReturn(workoutTemplateDTO);
        var result = controller.show(1L);

        assertEquals(result.getName(), workoutTemplateDTO.getName());
        assertEquals(result.getDescription(), workoutTemplateDTO.getDescription());
        assertThat(result.getExercises()).isNotNull();
        assertThat(result.getExercises()).hasSize(2);
        assertEquals(result.getExercises().get(0).getName(), EX_NAME);
        assertEquals(result.getExercises().get(1).getName(), "Name");
        assertEquals(result.getExercises().get(0).getDescription(), EX_DESC);
        assertEquals(result.getExercises().get(1).getDescription(), "Desc");

        verify(service).show(1L);
    }

    @Test
    void handleShowWTById_ReturnsNotFound() throws WorkoutTemplateNotFoundException {
        when(service.show(1L)).thenThrow(new WorkoutTemplateNotFoundException("Workout not found"));
        assertThrows(WorkoutTemplateNotFoundException.class, () -> controller.show(1L));

        verify(service).show(1L);
    }

    @Test
    void handleCreateWT_ReturnsOk() {
        //given
        var requestWT = new WorkoutTemplateCreateDTO();
        requestWT.setName(WT_NAME);
        requestWT.setDescription(WT_DESC);
        var exList = Arrays.asList(1L, 2L);
        requestWT.setExercisesIds(exList);

        when(service.create(requestWT)).thenReturn(workoutTemplateDTO);
        //when
        var result = controller.create(requestWT);

        //then
        assertEquals(result.getName(), requestWT.getName());
        assertEquals(result.getDescription(), requestWT.getDescription());
        assertThat(result.getExercises()).isNotNull();
        assertThat(result.getExercises()).hasSize(2);
        assertEquals(result.getExercises().get(0).getName(), EX_NAME);
        assertEquals(result.getExercises().get(0).getMuscleGroup(), MuscleGroup.CHEST);

        verify(service).create(requestWT);
    }

    @Test
    void handleShowAllWT_ReturnsOk() {
        //given
        var workoutTemplateDTO2 = new WorkoutTemplateDTO();
        workoutTemplateDTO2.setName("WT name");
        workoutTemplateDTO2.setDescription("WT desc");
        workoutTemplateDTO2.setExercises(new ArrayList<>());

        var WTList = Arrays.asList(workoutTemplateDTO, workoutTemplateDTO2);
        when(service.showAll()).thenReturn(WTList);

        //when
        var result = controller.showAll();

        //then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertEquals(result.get(0).getName(), workoutTemplateDTO.getName());
        assertEquals(result.get(1).getName(), workoutTemplateDTO2.getName());
        assertEquals(result.get(0).getDescription(), workoutTemplateDTO.getDescription());
        assertEquals(result.get(1).getDescription(), workoutTemplateDTO2.getDescription());

        verify(service).showAll();
    }


}