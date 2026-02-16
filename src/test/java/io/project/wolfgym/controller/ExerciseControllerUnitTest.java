package io.project.wolfgym.controller;

import io.project.wolfgym.customException.ExerciseNotFoundException;
import io.project.wolfgym.dto.exercise.ExerciseCreateDTO;
import io.project.wolfgym.dto.exercise.ExerciseDTO;
import io.project.wolfgym.model.MuscleGroup;
import io.project.wolfgym.service.ExerciseService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExerciseControllerUnitTest {
    @Mock
    ExerciseService service;

    @InjectMocks
    ExerciseController controller;

    private final static String EX_NAME = "Жим ногами";
    private final static String EX_DESC = "4 подхода по 10 раз";

    private ExerciseDTO exerciseDTO;

    @BeforeEach
    void setUp() {
        exerciseDTO = new ExerciseDTO();
        this.exerciseDTO.setName(EX_NAME);
        this.exerciseDTO.setDescription(EX_DESC);
        this.exerciseDTO.setMuscleGroup(MuscleGroup.CHEST);
    }


    @Test
    void handleCreateNewExercise_ReturnsOk() {
        //given
        var exerciseRequest = new ExerciseCreateDTO();
        exerciseRequest.setName(EX_NAME);
        exerciseRequest.setDescription(EX_DESC);
        exerciseRequest.setMuscleGroup(MuscleGroup.CHEST);

        when(service.create(exerciseRequest)).thenReturn(exerciseDTO);

        //when
        ExerciseDTO result = controller.create(exerciseRequest);

        //then
        assertEquals(EX_NAME, result.getName());
        assertEquals(EX_DESC, result.getDescription());
        assertEquals(MuscleGroup.CHEST, result.getMuscleGroup());

        verify(service).create(exerciseRequest);
    }

    @Test
    void handleGetExerciseById_ReturnsOk() throws ExerciseNotFoundException {
        when(service.show(1L)).thenReturn(exerciseDTO);

        var result = controller.show(1L);

        assertEquals(result.getName(), exerciseDTO.getName());
        assertEquals(result.getDescription(), exerciseDTO.getDescription());
        assertEquals(result.getMuscleGroup(), exerciseDTO.getMuscleGroup());

        verify(service).show(1L);
    }

//    @Test
//    void handleGetAllExercises_ReturnsOk() {
//        //given
//        var exerciseDTO2 = new ExerciseDTO();
//        exerciseDTO2.setName("Name");
//        exerciseDTO2.setDescription("Desc");
//        exerciseDTO2.setMuscleGroup(MuscleGroup.LEGS);
//
//        var exercisesList = Arrays.asList(exerciseDTO, exerciseDTO2);
//        when(service.showAll()).thenReturn(exercisesList);
//
//        //when
//        var result = controller.getExercises(null);
//
//        //then
//        assertThat(result).isNotNull();
//        assertThat(result).hasSize(2);
//        assertEquals(result.get(0).getName(), EX_NAME);
//        assertEquals(result.get(0).getMuscleGroup(), MuscleGroup.CHEST);
//        assertEquals(result.get(1).getName(), "Name");
//        assertEquals(result.get(1).getMuscleGroup(), MuscleGroup.LEGS);
//
//        verify(service).showAll();
//    }

    @Test
    void handleGetExerciseById_ReturnsNotFound() throws ExerciseNotFoundException {
        //given
        when(service.show(1L)).thenThrow(new ExerciseNotFoundException("Exercise not found"));
        //when then
        assertThrows(ExerciseNotFoundException.class, () -> controller.show(1L));

        verify(service).show(1L);
    }

    @SneakyThrows
    @Test
    void handleGetExerciseByName_ReturnsOk() {
        when(service.getExerciseByName(EX_NAME)).thenReturn(exerciseDTO);

        var result = controller.getExerciseByName("Жим ногами");

        assertEquals(EX_NAME, result.getName());
        verify(service).getExerciseByName(EX_NAME);
    }

    @SneakyThrows
    @Test
    void handleGetExerciseByName_ThrowsException() {
        when(service.getExerciseByName("Становая тяга"))
                .thenThrow(new ExerciseNotFoundException("Exercise not found by name: Становая тяга"));
        assertThrows(ExerciseNotFoundException.class, () -> controller.getExerciseByName("Становая тяга"));
        verify(service).getExerciseByName("Становая тяга");
    }


}