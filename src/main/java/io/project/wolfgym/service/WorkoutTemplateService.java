package io.project.wolfgym.service;

import io.project.wolfgym.customException.WorkoutTemplateNotFoundException;
import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateCreateDTO;
import io.project.wolfgym.dto.workoutTemplate.WorkoutTemplateDTO;
import io.project.wolfgym.mapper.WorkoutTemplateMapper;
import io.project.wolfgym.model.Exercise;
import io.project.wolfgym.model.WorkoutTemplate;
import io.project.wolfgym.repository.ExerciseRepository;
import io.project.wolfgym.repository.WorkoutTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutTemplateService {
    private final WorkoutTemplateMapper mapper;
    private final WorkoutTemplateRepository repository;
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public WorkoutTemplateDTO create(WorkoutTemplateCreateDTO createDTO) {

        WorkoutTemplate workoutTemplate = new WorkoutTemplate();
        workoutTemplate.setName(createDTO.getName());
        workoutTemplate.setDescription(createDTO.getDescription());

        // Устанавливаем упражнения через кастомные методы для двусторонней связи
        if (createDTO.getExercisesIds() != null) {
            List<Exercise> exercises = exerciseRepository.findAllById(createDTO.getExercisesIds());
            exercises.forEach(workoutTemplate::addExercise); // Это установит двустороннюю связь
        }

        WorkoutTemplate saved = repository.save(workoutTemplate);
        return mapper.map(saved);
    }

    public WorkoutTemplateDTO show(Long id) throws WorkoutTemplateNotFoundException{
        var workoutTemplate = repository.findById(id)
                .orElseThrow(() -> new WorkoutTemplateNotFoundException("Workout template not found"));
        return mapper.map(workoutTemplate);
    }

    public List<WorkoutTemplateDTO> showAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    public void destroy(Long id){
        repository.deleteById(id);
    }
    public WorkoutTemplateDTO getTemplateByName(String name){
        var template = repository.findByName(name);
        return mapper.map(template);
    }
}
