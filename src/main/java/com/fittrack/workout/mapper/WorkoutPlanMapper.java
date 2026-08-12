package com.fittrack.workout.mapper;

import com.fittrack.workout.domain.WorkoutPlan;
import com.fittrack.workout.dto.WorkoutPlanResponse;
import org.springframework.stereotype.Component;

@Component
public class WorkoutPlanMapper {
	public WorkoutPlanResponse toWorkoutPlanResponse(WorkoutPlan workoutPlan) {
		return new WorkoutPlanResponse(
				workoutPlan.getId(),
				workoutPlan.getName(),
				workoutPlan.getDescription(),
				workoutPlan.getStartDate(),
				workoutPlan.getEndDate(),
				workoutPlan.getActive());
		
	}
}
