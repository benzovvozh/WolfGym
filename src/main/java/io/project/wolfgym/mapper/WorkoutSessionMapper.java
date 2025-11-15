package io.project.wolfgym.mapper;

import io.project.wolfgym.customException.WorkoutTemplateNotFoundException;
import io.project.wolfgym.dto.exercise.ExerciseDTO;
import io.project.wolfgym.dto.workoutSession.WorkoutSessionCreateDTO;
import io.project.wolfgym.dto.workoutSession.WorkoutSessionDTO;
import io.project.wolfgym.dto.workoutSession.WorkoutSessionUpdateDTO;
import io.project.wolfgym.dto.workoutSet.WorkoutSetDTO;
import io.project.wolfgym.model.Exercise;
import io.project.wolfgym.model.WorkoutSession;
import io.project.wolfgym.model.WorkoutSet;
import io.project.wolfgym.model.WorkoutTemplate;
import io.project.wolfgym.repository.WorkoutSetRepository;
import io.project.wolfgym.repository.WorkoutTemplateRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class, ExerciseMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class WorkoutSessionMapper {
    @Autowired
    private WorkoutSetRepository workoutSetRepository;
    @Autowired
    private WorkoutTemplateRepository workoutTemplateRepository;
    @Autowired
    private WorkoutSetMapper workoutSetMapper;

    @Mapping(target = "template", source = "templateId")
    public abstract WorkoutSession map(WorkoutSessionCreateDTO createDTO);

    // надо подходы маппить в дто
    @Mapping(target = "workoutSetDTOList", source = "sets")
    public abstract WorkoutSessionDTO map(WorkoutSession session);

    @Mapping(target = "sets", source = "setsId")
    public abstract void update(@MappingTarget WorkoutSession session,WorkoutSessionUpdateDTO updateDTO);

//
    // Метод для преобразования ID → WorkoutSet
    protected List<WorkoutSet> mapWorkoutSetIdToWorkoutSet(List<Long> workoutSetIds) {

        if (workoutSetIds == null || workoutSetIds.isEmpty()) {
            return new ArrayList<>();
        }
        return workoutSetRepository.findAllById(workoutSetIds);
    }
    // Метод для преобразования WorkoutSet -> WorkoutSetDTO
    protected List<WorkoutSetDTO> mapWorkoutSetToWorkoutSetDTO(List<WorkoutSet> workoutSets){
        if (workoutSets == null || workoutSets.isEmpty()) {
            return new ArrayList<>();
        }
        var result = workoutSets.stream()
                .map(workoutSetMapper::toDTO)
                .toList();
        return result;
    }

    // Метод для преобразования Id шаблона в шаблон
    protected WorkoutTemplate mapWorkoutTemplateIdToWorkoutTemplate(Long templateId)
            throws WorkoutTemplateNotFoundException {
        return workoutTemplateRepository.findById(templateId)
                .orElseThrow(() -> new WorkoutTemplateNotFoundException("Ошибка в маппере: шаблон не найден"));
    }

}
