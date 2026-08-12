package com.fittrack.workout.repository;

import com.fittrack.workout.domain.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, UUID> {
	List<WorkoutLog> findByUserId(UUID userId);
	List<WorkoutLog> findByWorkoutPlanId(UUID workoutPlanId);
}
