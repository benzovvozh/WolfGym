package io.project.wolfgym.repository;

import io.project.wolfgym.model.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long>, JpaSpecificationExecutor<WorkoutSet> {
}
