package io.project.wolfgym.controller;

import io.project.wolfgym.customException.ExerciseNotFoundException;
import io.project.wolfgym.customException.WorkoutTemplateInUseException;
import io.project.wolfgym.customException.WorkoutTemplateNotFoundException;
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
    public WorkoutTemplateDTO create(@RequestBody @Valid WorkoutTemplateCreateDTO createDTO) throws ExerciseNotFoundException {
        return service.create(createDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkoutTemplateDTO show(@PathVariable("id") Long id) throws WorkoutTemplateNotFoundException {
        return service.show(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws WorkoutTemplateNotFoundException,
            WorkoutTemplateInUseException {
        service.destroy(id);
    }

    @DeleteMapping("/{templateId}/delete-exercise/{exerciseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExercise(@PathVariable("templateId") Long templateId, @PathVariable("exerciseId") Long exerciseId)
            throws WorkoutTemplateNotFoundException, ExerciseNotFoundException {
        service.removeExercise(templateId, exerciseId);
    }
    @DeleteMapping("/{id}/delete-exercise")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllExercises(@PathVariable("id") Long id) throws WorkoutTemplateNotFoundException {
        service.removeAllExercises(id);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkoutTemplateDTO> showAll() {
        return service.showAll();
    }

    @GetMapping("/search")
    public WorkoutTemplateDTO getTemplateByName(@RequestParam(required = false) String name)
            throws WorkoutTemplateNotFoundException {
        return service.getTemplateByName(name);
    }
}
