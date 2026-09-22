package com.fittrack.workout.repository;

import com.fittrack.workout.domain.Exercise;
import com.fittrack.workout.domain.WorkoutExercise;
import com.fittrack.workout.domain.WorkoutSetLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutSetLogRepository extends JpaRepository<WorkoutSetLog, UUID> {
	List<WorkoutSetLog> findByWorkoutLogId(UUID workoutLogId);
	List<WorkoutSetLog> findByWorkoutExercise_Exercise_IdAndWorkoutLog_User_Id(UUID exerciseId, UUID userId);
}
