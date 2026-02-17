package io.project.wolfgym.dto.workoutTemplate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WorkoutTemplateCreateDTO {
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    private String description;
    @NotNull(message = "Список упражнений не может быть null")
    private List<Long> exercisesIds = new ArrayList<>();
    @NotBlank(message = "ID пользователя не может быть пустым")
    private String userId;
}
