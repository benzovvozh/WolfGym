package io.project.wolfgym.dto.workoutSession;

import io.project.wolfgym.dto.workoutSet.WorkoutSetDTO;
import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkoutSessionDTO {
    private Long id;
    private String createdBy;
    private WorkoutTemplateDTO template;
    private Integer duration;
    private List<WorkoutSetDTO> workoutSetDTOList;
}
