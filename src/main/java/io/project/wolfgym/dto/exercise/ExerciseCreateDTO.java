package io.project.wolfgym.dto.exercise;

import io.project.wolfgym.model.MuscleGroup;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseCreateDTO {
    private String name;
    private String description;
    private MuscleGroup muscleGroup;
    private String videoUrl;
}
