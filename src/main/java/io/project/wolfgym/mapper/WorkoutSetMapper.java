package io.project.wolfgym.mapper;

import io.project.wolfgym.dto.workoutSet.WorkoutSetCreateDTO;
import io.project.wolfgym.dto.workoutSet.WorkoutSetDTO;
import io.project.wolfgym.model.WorkoutSet;
import org.mapstruct.*;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class, ExerciseMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class WorkoutSetMapper {

//    @Mapping(target = "workoutSession", source = "workoutSessionId")
//    public abstract WorkoutSet map(WorkoutSetCreateDTO createDTO);
//
//    public abstract WorkoutSetDTO map(WorkoutSet workoutSet);
}
