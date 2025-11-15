package io.project.wolfgym.dto.workoutSet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutSetCreateDTO {
    private Long workoutSessionId;
    private Long exerciseId;
    private Integer setNumber;
    private Double weight;
    private Integer reps;
    private String createdBy;
}
