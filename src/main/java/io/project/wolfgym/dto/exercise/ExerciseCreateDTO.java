package io.project.wolfgym.dto.exercise;

import io.project.wolfgym.model.MuscleGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseCreateDTO {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private MuscleGroup muscleGroup;
    private String videoUrl;
}
