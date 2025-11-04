package io.project.wolfgym.dto.workoutTemplate;

import io.project.wolfgym.model.Exercise;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WorkoutTemplateCreateDTO {
    private String name;
    private String description;
    private List<Exercise> exercises = new ArrayList<>();
}
