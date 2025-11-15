package io.project.wolfgym.dto.workoutSet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutSetCreateDTO {
    private Long workoutSessionId;
    private Double weight;
    private Integer reps;
}
