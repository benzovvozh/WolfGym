package io.project.wolfgym.service;

import io.project.wolfgym.dto.workoutSet.WorkoutSetCreateDTO;
import io.project.wolfgym.dto.workoutSet.WorkoutSetDTO;
import io.project.wolfgym.mapper.WorkoutSetMapper;
import io.project.wolfgym.model.Exercise;
import io.project.wolfgym.model.WorkoutSession;
import io.project.wolfgym.model.WorkoutSet;
import io.project.wolfgym.repository.ExerciseRepository;
import io.project.wolfgym.repository.WorkoutSessionRepository;
import io.project.wolfgym.repository.WorkoutSetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkoutSetService {
    private final WorkoutSetRepository workoutSetRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutSetMapper workoutSetMapper;

    public WorkoutSetDTO createWorkoutSet(WorkoutSetCreateDTO createDTO) {

//        WorkoutSession workoutSession = workoutSessionRepository.findById(createDTO.getWorkoutSessionId())
//                .orElseThrow(() -> new RuntimeException()); // исправить на кастомное исключение
//        Exercise exercise = exerciseRepository.findById(createDTO.getExerciseId())
//                .orElseThrow(() -> new RuntimeException()); // исправить на кастомное исключение

        WorkoutSet workoutSet = workoutSetMapper.toEntity(createDTO);
        workoutSet.setWorkoutSession(workoutSessionRepository.getReferenceById(createDTO.getWorkoutSessionId()));
        workoutSet.setExercise(exerciseRepository.getReferenceById(createDTO.getExerciseId()));

        WorkoutSet saved = workoutSetRepository.save(workoutSet);
        return workoutSetMapper.toDTO(saved);
    }
}
