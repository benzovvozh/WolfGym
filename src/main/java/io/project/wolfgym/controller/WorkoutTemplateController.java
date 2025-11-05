package io.project.wolfgym.controller;

import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateCreateDTO;
import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateDTO;
import io.project.wolfgym.service.WorkoutTemplateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workout-templates")
public class WorkoutTemplateController {
    private WorkoutTemplateService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutTemplateDTO create(@RequestBody @Valid WorkoutTemplateCreateDTO createDTO) {
        return service.create(createDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkoutTemplateDTO show(@PathVariable("id") Long id) {
        return service.show(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.destroy(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkoutTemplateDTO> showAll() {
        return service.showAll();
    }
}
