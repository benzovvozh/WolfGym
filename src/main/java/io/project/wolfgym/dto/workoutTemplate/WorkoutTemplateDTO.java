package io.project.wolfgym.dto.workoutTemplate;

import io.project.wolfgym.dto.exercise.ExerciseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class WorkoutTemplateDTO {
    private Long workoutTemplateId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<ExerciseDTO> exercises;
    private String userId;
}
