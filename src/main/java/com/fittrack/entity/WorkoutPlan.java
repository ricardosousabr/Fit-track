package com.fittrack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "workout_plan")
public class WorkoutPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private LocalDate start_date;
	
	@Column
	private LocalDate end_date;
	
	@Column(nullable = false)
	private Boolean active;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime created_at;
}
