package io.project.wolfgym.controller;

import io.project.wolfgym.dto.workoutSet.WorkoutSetCreateDTO;
import io.project.wolfgym.dto.workoutSet.WorkoutSetDTO;
import io.project.wolfgym.model.WorkoutSet;
import io.project.wolfgym.service.WorkoutSetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkoutSetControllerUnitTest {
    @Mock
    WorkoutSetService service;
    @InjectMocks
    WorkoutSetController controller;

    private final static String NAME_ADMIN = "Admin";
    private final static Double BASE_WEIGHT = 15.0;
    private final static Integer BASE_REPS = 10;
    private final static Long BASE_WS_ID = 1L;
    private final static Long BASE_EXERCISE_ID = 1L;
    private final static Integer BASE_SET_NUMBER = 1;
    private WorkoutSetDTO setDTO;
    private WorkoutSetCreateDTO setCreateDTO;

    @BeforeEach
    void setUp() {
        setDTO = new WorkoutSetDTO();
        setDTO.setWorkoutSessionId(1L);
        setDTO.setReps(BASE_REPS);
        setDTO.setSetNumber(BASE_SET_NUMBER);
        setDTO.setWeight(BASE_WEIGHT);
        setDTO.setWorkoutSessionId(BASE_WS_ID);
        setDTO.setExerciseId(BASE_EXERCISE_ID);
        setDTO.setCreatedBy(NAME_ADMIN);

        setCreateDTO = new WorkoutSetCreateDTO();
        setCreateDTO.setSetNumber(BASE_SET_NUMBER);
        setCreateDTO.setCreatedBy(NAME_ADMIN);
        setCreateDTO.setWeight(BASE_WEIGHT);
        setCreateDTO.setReps(BASE_REPS);
        setCreateDTO.setExerciseId(BASE_EXERCISE_ID);
        setCreateDTO.setWorkoutSessionId(BASE_WS_ID);
    }

    @Test
    void handleCreateNewSet_ReturnOk() {
        //given
        when(service.createWorkoutSet(setCreateDTO)).thenReturn(setDTO);

        //when
        var result = controller.create(setCreateDTO);

        //then
        assertEquals(result.getCreatedBy(), setDTO.getCreatedBy());
        assertEquals(result.getWeight(), setDTO.getWeight());
        assertEquals(result.getExerciseId(), setDTO.getExerciseId());

        verify(service).createWorkoutSet(setCreateDTO);
    }

}