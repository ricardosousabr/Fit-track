package com.fittrack.workout.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "workout_set_logs")
public class WorkoutSetLog {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workout_log_id", nullable = false)
	private WorkoutLog workoutLog;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workout_exercise_id", nullable = false)
	private WorkoutExercise workoutExercise;
	
	@Column(name = "set_number")
	private int setNumber;
	
	@Column(name = "reps_done")
	private int repsDone;
	
	@Column(name = "weight_done")
	private BigDecimal weightDone;
	
	@Column
	private BigDecimal rpe;
	
	@Column
	private Boolean completed;
}
