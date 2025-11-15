package io.project.wolfgym.dto.workoutSet;

import io.project.wolfgym.model.Exercise;
import io.project.wolfgym.model.WorkoutSession;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutSetDTO {

    private WorkoutSession workoutSession;

    private String createdBy;

    private Exercise exercise;

    private Double weight;

    private Integer reps;

    private Integer setNumber;
}
