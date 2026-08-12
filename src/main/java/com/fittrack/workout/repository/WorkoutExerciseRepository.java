package com.fittrack.workout.repository;

import com.fittrack.workout.domain.WorkoutExercise;
import com.fittrack.workout.domain.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, UUID> {
	List<WorkoutExercise> findByWorkoutPlan(WorkoutPlan workoutPlan);
	List<WorkoutExercise> findByWorkoutPlanId(UUID workoutPlanId);
	Optional<WorkoutExercise> findByWorkoutPlanIdAndExerciseId(UUID workoutPlanId, UUID exerciseId);
	
}
