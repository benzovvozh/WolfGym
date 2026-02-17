package io.project.wolfgym.repository;

import io.project.wolfgym.model.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate, Long>,
        JpaSpecificationExecutor<WorkoutTemplate> {

    @Query("select wt from WorkoutTemplate wt left join fetch wt.exercises where wt.name = :name")
    Optional<WorkoutTemplate> findByNameWithExercises(@Param("name") String name);

    @Query("select distinct wt from WorkoutTemplate wt left join fetch wt.exercises")
    List<WorkoutTemplate> findAllWithExercises();
}
