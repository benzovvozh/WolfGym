package io.project.wolfgym.service;

import io.project.wolfgym.customException.ExerciseNotFoundException;
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

    public ExerciseDTO show(Long id) throws ExerciseNotFoundException{
        var exercise = repository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise with id " + id + " not found"));
        return mapper.map(exercise);
    }

    public List<ExerciseDTO> showAll() {
        return repository.findAll().stream()
                .map(mapper::map).toList();
    }

    public void destroy(Long id){
        repository.deleteById(id);
    }
}
