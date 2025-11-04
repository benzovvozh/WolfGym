package io.project.wolfgym.service;

import io.project.wolfgym.dto.exercise.ExerciseCreateDTO;
import io.project.wolfgym.dto.exercise.ExerciseDTO;
import io.project.wolfgym.mapper.ExerciseMapper;
import io.project.wolfgym.model.Exercise;
import io.project.wolfgym.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    private ExerciseMapper mapper;
    private ExerciseRepository repository;

    public ExerciseService(ExerciseMapper mapper, ExerciseRepository repository) {
        this.repository = repository;
        this.mapper = mapper;
    }

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
