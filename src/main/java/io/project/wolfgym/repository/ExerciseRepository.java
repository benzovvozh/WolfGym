package io.project.wolfgym.repository;

import io.project.wolfgym.model.Exercise;
import io.project.wolfgym.model.MuscleGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long>, JpaSpecificationExecutor<Exercise> {

    // Более гибкий метод с пагинацией
    List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup, Pageable pageable);

    Optional<Exercise> findByNameIgnoreCase(String name);

    List<Exercise> findExercisesByNameContainsIgnoreCase(String name);

    List<Exercise> findByCreatedBy(String createdBy);
}
