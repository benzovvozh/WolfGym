package io.project.wolfgym.controller;

import io.project.wolfgym.customException.ExerciseNotFoundException;
import io.project.wolfgym.dto.exercise.ExerciseCreateDTO;
import io.project.wolfgym.dto.exercise.ExerciseDTO;
import io.project.wolfgym.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/exercises")
@AllArgsConstructor
public class ExerciseController {

    private ExerciseService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseDTO create(@RequestBody @Valid ExerciseCreateDTO exerciseCreateDTO) {
        return service.create(exerciseCreateDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExerciseDTO show(@PathVariable("id") Long id) throws ExerciseNotFoundException {
        return service.show(id);
    }
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<ExerciseDTO> showAll() {
//        return service.showAll();
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        service.destroy(id);
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getExercises(
            @RequestParam(required = false) String muscleGroup,
            @RequestParam(required = false) String createdBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        // Если указана группа мышц - ищем по группе
        if (muscleGroup != null && !muscleGroup.isEmpty()) {
            List<ExerciseDTO> exercises = service.getExercisesByMuscleGroup(muscleGroup, page, size);
            return ResponseEntity.ok(exercises);
        }
        if (createdBy != null && !createdBy.isEmpty()){
            List<ExerciseDTO> exercises = service.getExercisesByCreatedBy(createdBy);
            return ResponseEntity.ok(exercises);
        }

        // Если не указаны параметры - возвращаем все упражнения
        List<ExerciseDTO> allExercises = service.showAll();
        return ResponseEntity.ok(allExercises);
    }
    @GetMapping("/search")
    public ExerciseDTO getExerciseByName(@RequestParam(required = false) String name) throws ExerciseNotFoundException {
        return service.getExerciseByName(name);
    }

}
