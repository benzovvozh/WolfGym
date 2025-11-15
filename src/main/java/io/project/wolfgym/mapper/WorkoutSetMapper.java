package io.project.wolfgym.mapper;

import io.project.wolfgym.dto.workoutSet.WorkoutSetCreateDTO;
import io.project.wolfgym.dto.workoutSet.WorkoutSetDTO;
import io.project.wolfgym.model.Exercise;
import io.project.wolfgym.model.WorkoutSession;
import io.project.wolfgym.model.WorkoutSet;
import org.mapstruct.*;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class, ExerciseMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class WorkoutSetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workoutSession", source = "workoutSessionId", qualifiedByName = "mapWorkoutSession")
    @Mapping(target = "exercise", source = "exerciseId", qualifiedByName = "mapExercise")
    public abstract WorkoutSet toEntity(WorkoutSetCreateDTO createDTO);

    @Mapping(target = "workoutSessionId", source = "workoutSession.id")
    @Mapping(target = "exerciseId", source = "exercise.id")
    @Mapping(target = "exerciseName", source = "exercise.name")
    public abstract WorkoutSetDTO toDTO(WorkoutSet workoutSet);

    @Named("mapWorkoutSession")
    public WorkoutSession mapWorkoutSession(Long workoutSessionId) {
        if (workoutSessionId == null) return null;
        WorkoutSession session = new WorkoutSession();
        session.setId(workoutSessionId);
        return session;
    }

    @Named("mapExercise")
    public Exercise mapExercise(Long exerciseId) {
        if (exerciseId == null) return null;
        Exercise exercise = new Exercise();
        exercise.setId(exerciseId);
        return exercise;
    }
}
