package com.fittrack.repository;

import com.fittrack.entity.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, UUID> {
	List<WorkoutLog> findByUserId(UUID userId);
	List<WorkoutLog> findByWorkoutPlanId(UUID workoutPlanId);
}
