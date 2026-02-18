package io.project.wolfgym.mapper;

import io.project.wolfgym.dto.workoutSession.WorkoutSessionDTO;
import io.project.wolfgym.model.WorkoutSession;
import org.mapstruct.*;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class, ExerciseMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class WorkoutSessionMapper {

    // надо подходы маппить в дто
    @Mapping(target = "workoutSetDTOList", source = "sets")
    public abstract WorkoutSessionDTO map(WorkoutSession session);

}
