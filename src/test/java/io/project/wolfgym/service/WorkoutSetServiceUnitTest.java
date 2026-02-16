package io.project.wolfgym.service;

import io.project.wolfgym.customException.ExerciseNotFoundException;
import io.project.wolfgym.customException.WorkoutSessionNotFoundException;
import io.project.wolfgym.dto.workoutSet.WorkoutSetCreateDTO;
import io.project.wolfgym.dto.workoutSet.WorkoutSetDTO;
import io.project.wolfgym.mapper.WorkoutSetMapper;
import io.project.wolfgym.model.Exercise;
import io.project.wolfgym.model.WorkoutSession;
import io.project.wolfgym.model.WorkoutSet;
import io.project.wolfgym.repository.ExerciseRepository;
import io.project.wolfgym.repository.WorkoutSessionRepository;
import io.project.wolfgym.repository.WorkoutSetRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class WorkoutSetServiceUnitTest {
    @Mock
    WorkoutSetRepository workoutSetRepository;
    @Mock
    WorkoutSessionRepository workoutSessionRepository;
    @Mock
    WorkoutSetMapper workoutSetMapper;
    @Mock
    ExerciseRepository exerciseRepository;
    @InjectMocks
    WorkoutSetService workoutSetService;

    private WorkoutSetCreateDTO createDTO;
    private WorkoutSet workoutSet;
    private WorkoutSetDTO workoutSetDTO;
    private WorkoutSession workoutSession;
    private Exercise exercise;

    @BeforeEach
    void setUp() {
        // Prepare CreateDTO
        createDTO = new WorkoutSetCreateDTO();
        createDTO.setWorkoutSessionId(1L);
        createDTO.setExerciseId(1L);
        createDTO.setWeight(100.0);
        createDTO.setReps(10);
        createDTO.setSetNumber(1);
        createDTO.setCreatedBy("testUser");

        // Prepare entities
        workoutSession = new WorkoutSession();
        workoutSession.setId(1L);

        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Bench Press");

        workoutSet = new WorkoutSet();
        workoutSet.setId(1L);
        workoutSet.setWorkoutSession(workoutSession);
        workoutSet.setExercise(exercise);
        workoutSet.setWeight(100.0);
        workoutSet.setReps(10);
        workoutSet.setSetNumber(1);

        // Prepare DTO
        workoutSetDTO = new WorkoutSetDTO();
        workoutSetDTO.setWorkoutSessionId(1L);
        workoutSetDTO.setExerciseId(1L);
        workoutSetDTO.setWeight(100.0);
        workoutSetDTO.setReps(10);
        workoutSetDTO.setSetNumber(1);
    }

    @SneakyThrows
    @Test
    void createSet_withValidData_ReturnsOk() {
        //given
        when(workoutSetMapper.toEntity(createDTO)).thenReturn(workoutSet);
        when(workoutSessionRepository.findById(1L)).thenReturn(Optional.of(workoutSession));
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(workoutSetRepository.save(workoutSet)).thenReturn(workoutSet);
        when(workoutSetMapper.toDTO(workoutSet)).thenReturn(workoutSetDTO);

        //when
        WorkoutSetDTO result = workoutSetService.createWorkoutSet(createDTO);
        //then
        assertNotNull(result);
        assertEquals(100.0, result.getWeight());
        assertEquals(10, result.getReps());
        assertEquals(1, result.getSetNumber());
        assertEquals(1L, result.getExerciseId());
        assertEquals(1L, result.getWorkoutSessionId());

        verify(workoutSetMapper).toEntity(createDTO);
        verify(workoutSessionRepository).findById(1L);
        verify(exerciseRepository).findById(1L);
        verify(workoutSetRepository).save(workoutSet);
        verify(workoutSetMapper).toDTO(workoutSet);
    }

    @SneakyThrows
    @Test
    void createSet_WithInvalidSessionId_ThrowsException() {
        //given
        createDTO.setWorkoutSessionId(999L); // устанавливаем несуществующий id сессии
        when(workoutSessionRepository.findById(999L)).thenReturn(Optional.empty());
        //when
        assertThrows(WorkoutSessionNotFoundException.class,
                () -> workoutSetService.createWorkoutSet(createDTO));
        //then
        verify(workoutSessionRepository).findById(999L);
        verify(exerciseRepository, never()).findById(any());
        verify(workoutSetRepository, never()).save(any());
    }

    @SneakyThrows
    @Test
    void createSet_WithInvalidExerciseId_ThrowsException() {
        //given
        createDTO.setExerciseId(999L);
        when(workoutSetMapper.toEntity(createDTO)).thenReturn(workoutSet);
        when(workoutSessionRepository.findById(1L)).thenReturn(Optional.of(workoutSession));
        when(exerciseRepository.findById(999L)).thenReturn(Optional.empty());
        //when
        assertThrows(ExerciseNotFoundException.class,
                () -> workoutSetService.createWorkoutSet(createDTO));
        //then
        verify(workoutSessionRepository).findById(1L);
        verify(exerciseRepository).findById(999L);
        verify(workoutSetRepository, never()).save(any());
    }
}
