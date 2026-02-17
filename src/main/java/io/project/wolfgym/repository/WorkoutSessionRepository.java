package io.project.wolfgym.repository;

import io.project.wolfgym.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long>, JpaSpecificationExecutor<WorkoutSession> {

    @Query("select ws from WorkoutSession ws " +
           "left join fetch ws.sets " +
           "left join fetch ws.template t " +
           "left join fetch t.exercises")
    List<WorkoutSession> findAllWithSetsAndTemplate();

}
