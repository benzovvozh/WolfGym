package io.project.wolfgym.controller;

import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateCreateDTO;
import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateDTO;
import io.project.wolfgym.service.WorkoutTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workout-templates")
public class WorkoutTemplateController {
    private WorkoutTemplateService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutTemplateDTO create(@RequestBody WorkoutTemplateCreateDTO createDTO) {
        return service.create(createDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkoutTemplateDTO show(@PathVariable("id") Long id) {
        return service.show(id);
    }
}
