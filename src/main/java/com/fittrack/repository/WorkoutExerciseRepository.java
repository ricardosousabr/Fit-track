package com.fittrack.repository;

import com.fittrack.entity.WorkoutExercise;
import com.fittrack.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, UUID> {
	List<WorkoutExercise> findByWorkoutPlan(WorkoutPlan workoutPlan);
	List<WorkoutExercise> findByWorkoutPlanId(UUID workoutPlanId);
	
}
