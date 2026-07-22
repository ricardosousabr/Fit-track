package com.fittrack.repository;

import com.fittrack.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, UUID> {
	List<WorkoutPlan> findByUserId(UUID userId);
	List<WorkoutPlan> findByUserIdAndActiveTrue(UUID userId);
	
}
