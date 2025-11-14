package io.project.wolfgym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @CreatedDate
    @NotNull
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer duration; // Длительность тренировки

    private String userId;
    // Связь с подходами
    @OneToMany(mappedBy = "workoutSession", cascade = CascadeType.ALL)
    private List<WorkoutSet> sets = new ArrayList<>();

    /*
    1. создать сразу: старт и енд устанавливаются как now(),
        id, WT, userID тоже сразу
        после нажатия на последнюю кнопку - обновлять endTime + duration
     */
}
