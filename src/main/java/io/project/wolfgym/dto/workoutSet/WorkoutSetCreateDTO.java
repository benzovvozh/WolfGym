package io.project.wolfgym.dto.workoutSet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutSetCreateDTO {
    @NotNull(message = "ID сессии не может быть null")
    private Long workoutSessionId;
    @NotNull(message = "ID упражнения не может быть null")
    private Long exerciseId;
    @NotNull(message = "Номер подхода не может быть быть null")
    @Positive(message = "Номер подхода должен быть положительным")
    private Integer setNumber;
    @NotNull(message = "Вес не может быть null")
    @Positive(message = "Вес должен быть положительным")
    private Double weight;
    @NotNull(message = "Количество повторений не может быть null")
    @Positive(message = "Количество повторений должно быть положительным")
    private Integer reps;
    @NotBlank(message = "CreatedBy не может быть пустым")
    private String createdBy;
}
