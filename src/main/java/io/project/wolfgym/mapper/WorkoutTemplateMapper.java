package io.project.wolfgym.mapper;

import io.project.wolfgym.dto.exercise.ExerciseDTO;
import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateCreateDTO;
import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateDTO;
import io.project.wolfgym.model.Exercise;
import io.project.wolfgym.model.WorkoutTemplate;
import io.project.wolfgym.repository.ExerciseRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class, ExerciseMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class WorkoutTemplateMapper {
    @Autowired
    private ExerciseMapper exerciseMapper;

    @Mapping(target = "exercises", source = "exercises", qualifiedByName = "mapExercisesToDTOs")
    public abstract WorkoutTemplateDTO map(WorkoutTemplate workoutTemplate);

    @Named("mapExercisesToDTOs")
    protected List<ExerciseDTO> mapExercisesToDTOs(List<Exercise> exercises) {
        if (exercises == null || exercises.isEmpty()) {
            return new ArrayList<>();
        }
        return exercises.stream()
                .map(exerciseMapper::map)
                .collect(Collectors.toList());
    }
}
