package io.project.wolfgym.service;


import io.project.wolfgym.customException.WorkoutSessionNotFoundException;
import io.project.wolfgym.customException.WorkoutSetNotFoundException;
import io.project.wolfgym.customException.WorkoutTemplateNotFoundException;
import io.project.wolfgym.dto.workoutSession.WorkoutSessionCreateDTO;
import io.project.wolfgym.dto.workoutSession.WorkoutSessionDTO;
import io.project.wolfgym.dto.workoutSession.WorkoutSessionUpdateDTO;
import io.project.wolfgym.mapper.WorkoutSessionMapper;
import io.project.wolfgym.model.WorkoutSession;
import io.project.wolfgym.model.WorkoutSet;
import io.project.wolfgym.repository.WorkoutSessionRepository;
import io.project.wolfgym.repository.WorkoutSetRepository;
import io.project.wolfgym.repository.WorkoutTemplateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class WorkoutSessionService {
    private final WorkoutSessionRepository repository;
    private final WorkoutSessionMapper sessionMapper;
    private final WorkoutTemplateRepository templateRepository;
    private final WorkoutSetRepository setRepository;

    @Transactional
    public WorkoutSessionDTO create(WorkoutSessionCreateDTO createDTO) throws WorkoutTemplateNotFoundException {
        var template = templateRepository.findById(createDTO.getTemplateId())
                .orElseThrow(() ->
                        new WorkoutTemplateNotFoundException("Template not found with Id: " +
                                                             createDTO.getTemplateId()));
        WorkoutSession session = new WorkoutSession();
        session.setTemplate(template);
        session.setCreatedBy(createDTO.getCreatedBy());
        repository.save(session);
        return sessionMapper.map(session);
    }

    public WorkoutSessionDTO show(Long id) throws WorkoutSessionNotFoundException {
        var result = repository.findById(id).orElseThrow(() ->
                new WorkoutSessionNotFoundException("Session with id " + id + " not found"));
        return sessionMapper.map(result);
    }

    public List<WorkoutSessionDTO> showAll() {
        return repository.findAllWithSetsAndTemplate().stream()
                .map(sessionMapper::map)
                .toList();
    }

    @Transactional
    public void destroy(Long id) throws WorkoutSessionNotFoundException {
        if (!repository.existsById(id)) {
            throw new WorkoutSessionNotFoundException("Cannot delete. Workout session not found by ID: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public WorkoutSessionDTO update(WorkoutSessionUpdateDTO updateDTO, Long id) throws
            WorkoutSessionNotFoundException, WorkoutSetNotFoundException {

        WorkoutSession session = repository.findById(id)
                .orElseThrow(() ->
                        new WorkoutSessionNotFoundException("Session with  " + id + " not found"));

        // получаем все айди подходов
        List<Long> setIds = updateDTO.getSetsId();
        // получаем все подходы по айди
        List<WorkoutSet> foundSets = setRepository.findAllById(setIds);

        // если размер коллекций разный -> какие-то не нашлись
        if (setIds.size() != foundSets.size()) {
            // получаем айди всех найденных подходов
            var foundIds = foundSets.stream()
                    .map(WorkoutSet::getId)
                    .toList();
            // получаем айди всех ненайденных подходов
            var notFoundSets = setIds.stream()
                    .filter(setId -> !foundIds.contains(setId))
                    .toList();
            // бросаем исключение со списков ненайденных подходов
            throw new WorkoutSetNotFoundException("Подходы не найдены с ID: " + notFoundSets);
        }
        // добавляем все подходы в сессию
        foundSets.stream()
                .forEach(set -> session.addSet(set));
        repository.save(session);
        return sessionMapper.map(session);
    }

    @Transactional
    public WorkoutSessionDTO endSession(Long id) throws WorkoutSessionNotFoundException {
        var session = repository.findById(id).orElseThrow(() ->
                new WorkoutSessionNotFoundException("Session with id " + id + " not found"));
        var now = LocalDateTime.now();
        session.setEndTime(now);
        Duration duration = Duration.between(session.getStartTime(), now);
        session.setDuration((int) duration.toMinutes());
        repository.save(session);
        return sessionMapper.map(session);
    }

}
