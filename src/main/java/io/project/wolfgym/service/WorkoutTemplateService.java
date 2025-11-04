package io.project.wolfgym.service;

import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateCreateDTO;
import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateDTO;
import io.project.wolfgym.mapper.WorkoutTemplateMapper;
import io.project.wolfgym.model.WorkoutTemplate;
import io.project.wolfgym.repository.WorkoutTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutTemplateService {
    private WorkoutTemplateMapper mapper;
    private WorkoutTemplateRepository repository;

    public WorkoutTemplateService(WorkoutTemplateRepository repository,
                                  WorkoutTemplateMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public WorkoutTemplateDTO create(WorkoutTemplateCreateDTO createDTO) {
        WorkoutTemplate workoutTemplate = mapper.map(createDTO);
        repository.save(workoutTemplate);
        return mapper.map(workoutTemplate);
    }

    public WorkoutTemplateDTO show(Long id) {
        var workoutTemplate = repository.findById(id).orElseThrow();
        return mapper.map(workoutTemplate);
    }

    public List<WorkoutTemplateDTO> showAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }
}
