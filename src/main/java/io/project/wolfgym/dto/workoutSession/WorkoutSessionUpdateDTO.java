package io.project.wolfgym.dto.workoutSession;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkoutSessionUpdateDTO {
    private List<Long> setsId;
}
