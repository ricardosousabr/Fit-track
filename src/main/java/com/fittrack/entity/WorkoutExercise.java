package com.fittrack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "workout_exercise")
public class WorkoutExercise {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workout_plan_id", nullable = false)
	private WorkoutPlan workoutPlan;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exercise_id", nullable = false)
	private Exercise exercise;
	
	@Column
	private int sets;
	
	@Column
	private int reps;
	
	@Column
	private BigDecimal weight;
	
	@Column
	private BigDecimal rpe;
	
	@Column(name = "order_index")
	private int orderIndex;
	
	@Column
	private String notes;
}
