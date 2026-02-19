package io.project.wolfgym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
    Тренировка
 */
@Entity
@Table(name = "workout_session")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_template_id")
    private WorkoutTemplate template;

    private String createdBy;

    @CreatedDate
    @NotNull
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer duration; // Длительность тренировки

    // Связь с подходами
    @OneToMany(mappedBy = "workoutSession", cascade = CascadeType.ALL)
    private Set<WorkoutSet> sets = new HashSet<>();

    public void addSet(WorkoutSet set) {
        if (set == null){
            throw new IllegalArgumentException("WorkoutSet не может быть null");
        }
        if (!this.sets.contains(set)){
            this.sets.add(set);
            set.setWorkoutSession(this);
        }

    }
    public void  removeSet(WorkoutSet set){
        if (set == null){
            throw new IllegalArgumentException("WorkoutSet не может быть null");
        }
        if (this.sets.contains(set)){
            this.sets.remove(set);
        }

    }

    public Integer getCalculatedDuration() {
        if (startTime == null || endTime == null) {
            return null;
        }
        return (int) Duration.between(startTime, endTime).toMinutes();
    }

}
