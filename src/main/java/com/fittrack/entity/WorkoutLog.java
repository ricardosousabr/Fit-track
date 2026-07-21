package com.fittrack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "workout_logs")
public class WorkoutLog {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workout_plan_id", nullable = false)
	private WorkoutPlan workoutPlan;
	
	@CreationTimestamp
	@Column(name = "logged_at", nullable = false, updatable = false)
	private LocalDateTime loggedAt;
	
	@Column
	private String notes;
}
