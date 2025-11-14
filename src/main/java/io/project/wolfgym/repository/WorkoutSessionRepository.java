package io.project.wolfgym.repository;

import io.project.wolfgym.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long>, JpaSpecificationExecutor<WorkoutSession> {
}
