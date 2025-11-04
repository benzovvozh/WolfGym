package io.project.wolfgym.dto.exercise;

import io.project.wolfgym.model.MuscleGroup;
import io.project.wolfgym.model.WorkoutTemplate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExerciseDTO {
    private String name;
    private String description;
    private MuscleGroup muscleGroup;
    private String videoUrl;
    private LocalDateTime createdAt;
    private List<WorkoutTemplate> workoutTemplates = new ArrayList<>();
}
