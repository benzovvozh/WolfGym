package io.project.wolfgym.service;

import io.project.wolfgym.customException.ExerciseNotFoundException;
import io.project.wolfgym.customException.WorkoutTemplateInUseException;
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
@Transactional(readOnly = true)
public class WorkoutTemplateService {
    private final WorkoutTemplateMapper mapper;
    private final WorkoutTemplateRepository repository;
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public WorkoutTemplateDTO create(WorkoutTemplateCreateDTO createDTO) throws ExerciseNotFoundException {

        WorkoutTemplate workoutTemplate = new WorkoutTemplate();
        workoutTemplate.setName(createDTO.getName());
        workoutTemplate.setDescription(createDTO.getDescription());

        List<Long> exerciseIds = createDTO.getExercisesIds();
        if (exerciseIds == null || exerciseIds.isEmpty()) {
            throw new IllegalArgumentException("Exercise IDs cannot be null");
        }

        List<Exercise> foundExercises = exerciseRepository.findAllById(exerciseIds);
        if (exerciseIds.size() != foundExercises.size()) {
            var foundIds = foundExercises.stream()
                    .map(Exercise::getId)
                    .toList();
            var notFoundExercises = exerciseIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
            throw new ExerciseNotFoundException("Упражнения не найдены с ID: " + notFoundExercises);
        }
        foundExercises.stream()
                .forEach(exercise -> workoutTemplate.addExercise(exercise));
        WorkoutTemplate saved = repository.save(workoutTemplate);
        return mapper.map(saved);
    }

    public WorkoutTemplateDTO show(Long id) throws WorkoutTemplateNotFoundException {
        var workoutTemplate = repository.findByIdWithExercises(id)
                .orElseThrow(() -> new WorkoutTemplateNotFoundException("Workout template not found"));
        return mapper.map(workoutTemplate);
    }

    public List<WorkoutTemplateDTO> showAll() {
        return repository.findAllWithExercises().stream().map(mapper::map).toList();
    }

    @Transactional
    public void destroy(Long id) throws WorkoutTemplateNotFoundException, WorkoutTemplateInUseException {
        WorkoutTemplate workoutTemplate = repository.findByIdWithExercises(id)
                .orElseThrow(() ->
                        new WorkoutTemplateNotFoundException("Cannot delete. Workout template not found by ID: " + id));

        if (!workoutTemplate.getSessions().isEmpty()) {
            throw new WorkoutTemplateInUseException("Cannot delete template with existing workout sessions");
        }
        workoutTemplate.removeAllSessions();
        repository.delete(workoutTemplate);
    }

    @Transactional
    public void removeExercise(Long templateId, Long exerciseId) throws WorkoutTemplateNotFoundException,
            ExerciseNotFoundException {
        WorkoutTemplate workoutTemplate = repository.findByIdWithExercises(templateId)
                .orElseThrow(() ->
                        new WorkoutTemplateNotFoundException("Cannot delete. Workout template not found by ID: " +
                                                             templateId));
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found with id: " + exerciseId));
        workoutTemplate.removeExercise(exercise);
        repository.save(workoutTemplate);
    }

    @Transactional
    public void removeAllExercises(Long templateId) throws WorkoutTemplateNotFoundException {
        WorkoutTemplate workoutTemplate = repository.findByIdWithExercises(templateId)
                .orElseThrow(() ->
                        new WorkoutTemplateNotFoundException("Workout template not found by ID: " +
                                                             templateId));
        workoutTemplate.removeAllExercises();
        repository.save(workoutTemplate);
    }

    public WorkoutTemplateDTO getTemplateByName(String name) throws WorkoutTemplateNotFoundException {
        var template = repository.findByNameWithExercises(name)
                .orElseThrow(() -> new WorkoutTemplateNotFoundException("Workout template not found by name: " + name));
        return mapper.map(template);
    }
}
