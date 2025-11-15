package io.project.wolfgym.dto.workoutSession;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutSessionCreateDTO {
    private Long templateId;
    private String createdBy;
}
