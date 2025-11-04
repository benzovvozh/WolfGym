package io.project.wolfgym.repository;

import io.project.wolfgym.model.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate, Long>,
        JpaSpecificationExecutor<WorkoutTemplate> {
}
