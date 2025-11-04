package io.project.wolfgym.controller;

import io.project.wolfgym.dto.exercise.ExerciseCreateDTO;
import io.project.wolfgym.dto.exercise.ExerciseDTO;
import io.project.wolfgym.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/exercises")
@AllArgsConstructor
public class ExerciseController {
    private ExerciseService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseDTO create(@RequestBody ExerciseCreateDTO exerciseCreateDTO){
        return service.create(exerciseCreateDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExerciseDTO show(@PathVariable("id") Long id){
        return service.show(id);
    }

}
