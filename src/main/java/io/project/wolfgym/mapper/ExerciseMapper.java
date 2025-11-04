package io.project.wolfgym.mapper;
import org.mapstruct.*;
import io.project.wolfgym.dto.exercise.ExerciseCreateDTO;
import io.project.wolfgym.dto.exercise.ExerciseDTO;
import io.project.wolfgym.model.Exercise;
import org.springframework.stereotype.Component;


@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ExerciseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "workoutTemplates", ignore = true)
    public abstract Exercise map(ExerciseCreateDTO exerciseCreateDTO);

    public abstract ExerciseDTO map(Exercise exercise);
}
