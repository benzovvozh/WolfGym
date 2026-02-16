package io.project.wolfgym.dto.workoutTemplate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WorkoutTemplateCreateDTO {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private List<Long> exercisesIds = new ArrayList<>();
    private String userId;
}
