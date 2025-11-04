package io.project.wolfgym.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
Шаблон тренировки
 */
@Entity
@Table(name = "workout_template")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class WorkoutTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_template_id")
    private Long workoutTemplateId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "workout_template_exercise",
            joinColumns = @JoinColumn(name = "workout_template_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private List<Exercise> exercises = new ArrayList<>();

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
        exercise.getWorkoutTemplates().add(this);
    }

    public void removeExercise(Exercise exercise) {
        this.exercises.remove(exercise);
        exercise.getWorkoutTemplates().remove(this);
    }



}
