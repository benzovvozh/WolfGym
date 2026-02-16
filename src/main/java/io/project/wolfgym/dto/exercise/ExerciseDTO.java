package io.project.wolfgym.dto.exercise;

import io.project.wolfgym.model.MuscleGroup;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExerciseDTO {
    private Long id;
    private String name;
    private String description;
    private MuscleGroup muscleGroup;
    private String videoUrl;
    private LocalDateTime createdAt;
    private String createdBy;
}
