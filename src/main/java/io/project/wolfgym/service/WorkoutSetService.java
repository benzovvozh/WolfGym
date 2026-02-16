package io.project.wolfgym.service;

import io.project.wolfgym.customException.ExerciseNotFoundException;
import io.project.wolfgym.customException.WorkoutSessionNotFoundException;
import io.project.wolfgym.dto.workoutSet.WorkoutSetCreateDTO;
import io.project.wolfgym.dto.workoutSet.WorkoutSetDTO;
import io.project.wolfgym.mapper.WorkoutSetMapper;
import io.project.wolfgym.model.WorkoutSet;
import io.project.wolfgym.repository.ExerciseRepository;
import io.project.wolfgym.repository.WorkoutSessionRepository;
import io.project.wolfgym.repository.WorkoutSetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class WorkoutSetService {
    private final WorkoutSetRepository workoutSetRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutSetMapper workoutSetMapper;

    public WorkoutSetDTO createWorkoutSet(WorkoutSetCreateDTO createDTO) throws WorkoutSessionNotFoundException,
            ExerciseNotFoundException {

        WorkoutSet workoutSet = workoutSetMapper.toEntity(createDTO);

        var session = workoutSessionRepository.findById(createDTO.getWorkoutSessionId())
                .orElseThrow(() -> new WorkoutSessionNotFoundException(
                        "Session not found with id: " + createDTO.getWorkoutSessionId()));
        workoutSet.setWorkoutSession(session);

        var exercise = exerciseRepository.findById(createDTO.getExerciseId())
                .orElseThrow(() -> new ExerciseNotFoundException(
                        "Exercise not found with id: " + createDTO.getExerciseId()
                ));
        workoutSet.setExercise(exercise);

        WorkoutSet saved = workoutSetRepository.save(workoutSet);
        return workoutSetMapper.toDTO(saved);
    }
}
