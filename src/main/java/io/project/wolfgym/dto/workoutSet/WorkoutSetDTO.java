package io.project.wolfgym.dto.workoutSet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutSetDTO {
    private Long id;
    private Long workoutSessionId;
    private Long exerciseId;
    private String exerciseName;
    private String createdBy;
    private Double weight;
    private Integer reps;
    private Integer setNumber;
}
