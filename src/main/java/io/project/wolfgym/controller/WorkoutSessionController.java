package io.project.wolfgym.controller;

import io.project.wolfgym.customException.WorkoutSessionNotFoundException;
import io.project.wolfgym.customException.WorkoutSetNotFoundException;
import io.project.wolfgym.customException.WorkoutTemplateNotFoundException;
import io.project.wolfgym.dto.workoutSession.WorkoutSessionCreateDTO;
import io.project.wolfgym.dto.workoutSession.WorkoutSessionDTO;
import io.project.wolfgym.dto.workoutSession.WorkoutSessionUpdateDTO;
import io.project.wolfgym.service.WorkoutSessionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@AllArgsConstructor
public class WorkoutSessionController {
    private WorkoutSessionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutSessionDTO create(@RequestBody @Valid WorkoutSessionCreateDTO createDTO)
            throws WorkoutTemplateNotFoundException {
        return service.create(createDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkoutSessionDTO show(@PathVariable("id") Long id) throws WorkoutSessionNotFoundException{
        return service.show(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkoutSessionDTO> showAll() {
        return service.showAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws WorkoutSessionNotFoundException {
        service.destroy(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkoutSessionDTO update(@RequestBody @Valid WorkoutSessionUpdateDTO updateDTO)
            throws WorkoutSessionNotFoundException, WorkoutSetNotFoundException {
        return service.update(updateDTO);
    }

    @PatchMapping("/{id}/endSession")
    @ResponseStatus(HttpStatus.OK)
    public WorkoutSessionDTO endSession(@PathVariable("id") Long id) throws WorkoutSessionNotFoundException {
        return service.endSession(id);
    }

}
