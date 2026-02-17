package io.project.wolfgym.dto.exercise;

import io.project.wolfgym.model.MuscleGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseCreateDTO {
    @NotBlank(message = "Название упражнения не может быть пустым")
    private String name;
    private String description;
    @NotNull(message = "Группа мышц не может быть null")
    private MuscleGroup muscleGroup;
    private String videoUrl;
    @NotBlank(message = "CreatedBy не может быть пустым")
    private String createdBy;
}
