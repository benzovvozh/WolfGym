package io.project.wolfgym.controller;

import io.project.wolfgym.customException.ExerciseNotFoundException;
import io.project.wolfgym.customException.WorkoutSessionNotFoundException;
import io.project.wolfgym.dto.workoutSet.WorkoutSetCreateDTO;
import io.project.wolfgym.dto.workoutSet.WorkoutSetDTO;
import io.project.wolfgym.service.WorkoutSetService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout-sets")
@AllArgsConstructor
public class WorkoutSetController {
    private WorkoutSetService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutSetDTO create(@RequestBody @Valid WorkoutSetCreateDTO createDTO)
            throws WorkoutSessionNotFoundException, ExerciseNotFoundException {
        return service.createWorkoutSet(createDTO);
    }
}
