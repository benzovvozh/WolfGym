package io.project.wolfgym.service;


import io.project.wolfgym.customException.WorkoutSessionNotFoundException;
import io.project.wolfgym.dto.workoutSession.WorkoutSessionCreateDTO;
import io.project.wolfgym.dto.workoutSession.WorkoutSessionDTO;
import io.project.wolfgym.dto.workoutSession.WorkoutSessionUpdateDTO;
import io.project.wolfgym.mapper.WorkoutSessionMapper;
import io.project.wolfgym.repository.WorkoutSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class WorkoutSessionService {
    private final WorkoutSessionRepository repository;
    private final WorkoutSessionMapper sessionMapper;

    public WorkoutSessionDTO create(WorkoutSessionCreateDTO createDTO) {
        var result = sessionMapper.map(createDTO);
        repository.save(result);
        return sessionMapper.map(result);
    }

    public WorkoutSessionDTO show(Long id) throws WorkoutSessionNotFoundException{
        var result = repository.findById(id).orElseThrow(() ->
                new WorkoutSessionNotFoundException("Session with id " + id + " not found"));
        return sessionMapper.map(result);
    }

    public List<WorkoutSessionDTO> showAll() {
        return repository.findAll().stream()
                .map(sessionMapper::map)
                .toList();
    }

    public void destroy(Long id) {
        repository.deleteById(id);
    }

    public WorkoutSessionDTO update(WorkoutSessionUpdateDTO updateDTO) throws WorkoutSessionNotFoundException {
        var session = repository.findById(updateDTO.getId())
                .orElseThrow(() ->
                        new WorkoutSessionNotFoundException("Session with  " + updateDTO.getId() + " not found"));
        sessionMapper.update(session, updateDTO);
        repository.save(session);
        return sessionMapper.map(session);
    }

    public WorkoutSessionDTO endSession(Long id) throws WorkoutSessionNotFoundException{
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
