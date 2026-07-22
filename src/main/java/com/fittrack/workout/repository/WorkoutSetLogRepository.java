package com.fittrack.workout.repository;

import com.fittrack.workout.domain.WorkoutSetLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutSetLogRepository extends JpaRepository<WorkoutSetLog, UUID> {
	List<WorkoutSetLog> findByWorkoutLogId(UUID workoutLogId);
}
