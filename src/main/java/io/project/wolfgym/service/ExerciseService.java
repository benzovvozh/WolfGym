package io.project.wolfgym.service;

import io.project.wolfgym.dto.exercise.ExerciseCreateDTO;
import io.project.wolfgym.dto.exercise.ExerciseDTO;
import io.project.wolfgym.mapper.ExerciseMapper;
import io.project.wolfgym.model.Exercise;
import io.project.wolfgym.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
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

    public ExerciseDTO show(Long id) {
        var exercise = repository.findById(id).orElseThrow();
        return mapper.map(exercise);
    }

    public List<ExerciseDTO> showAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }
}
