package io.project.wolfgym.service;

import io.project.wolfgym.customException.ExerciseNotFoundException;
import io.project.wolfgym.dto.exercise.ExerciseCreateDTO;
import io.project.wolfgym.dto.exercise.ExerciseDTO;
import io.project.wolfgym.mapper.ExerciseMapper;
import io.project.wolfgym.model.Exercise;
import io.project.wolfgym.model.MuscleGroup;
import io.project.wolfgym.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseMapper mapper;
    private final ExerciseRepository repository;

    public ExerciseDTO create(ExerciseCreateDTO createDTO) {
        Exercise exercise = mapper.map(createDTO);
        repository.save(exercise);
        return mapper.map(exercise);
    }

    public ExerciseDTO show(Long id) throws ExerciseNotFoundException {
        var exercise = repository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise with id " + id + " not found"));
        return mapper.map(exercise);
    }

    public List<ExerciseDTO> showAll() {
        return repository.findAll().stream()
                .map(mapper::map).toList();
    }

    // Поиск с пагинацией
    public List<ExerciseDTO> getExercisesByMuscleGroup(String muscleGroup, int page, int size) {
        // Создаем объект пагинации
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        // Вызываем репозиторий с пагинацией
        var enumMuscleGroup = MuscleGroup.valueOf(muscleGroup);
        List<Exercise> exercises = repository.findByMuscleGroup(enumMuscleGroup, pageable);
        return exercises.stream()
                .map(mapper::map)
                .toList();
    }

    public List<ExerciseDTO> getExercisesByCreatedBy(String userId) {
        return repository.findByCreatedBy(userId).stream()
                .map(mapper::map)
                .toList();
    }

    public ExerciseDTO getExerciseByName(String name) throws ExerciseNotFoundException {
        var exercise = repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found by name: " + name));
        return mapper.map(exercise);
    }

    public List<ExerciseDTO> getExerciseByNameContains(String name) {
        return repository.findExercisesByNameContainsIgnoreCase(name)
                .stream()
                .map(mapper::map)
                .toList();
    }

    public void destroy(Long id) {
        repository.deleteById(id);
    }
}
