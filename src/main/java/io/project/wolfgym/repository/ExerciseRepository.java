package io.project.wolfgym.repository;

import io.project.wolfgym.model.Exercise;
import io.project.wolfgym.model.MuscleGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long>, JpaSpecificationExecutor<Exercise> {
    // Простой метод для поиска по группе мышц
    List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup);

    // Более гибкий метод с пагинацией
    List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup, Pageable pageable);

    Exercise findByName(String name);

    List<Exercise> findByCreatedBy(String createdBy);
}
