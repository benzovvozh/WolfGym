package io.project.wolfgym.dto.workoutSession;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutSessionCreateDTO {
    @NotNull(message = "ID шаблона не может быть null")
    private Long templateId;
    @NotBlank(message = "CreatedBy не может быть пустым")
    private String createdBy;
}
