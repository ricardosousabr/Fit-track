package com.fittrack.workout.mapper;

import com.fittrack.workout.domain.WorkoutExercise;
import com.fittrack.workout.dto.WorkoutExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkoutExerciseMapper {
	
	private final ExerciseMapper exerciseMapper;
	
	public WorkoutExerciseResponse toWorkoutExercise(WorkoutExercise workoutExercise) {
		return new WorkoutExerciseResponse(
				workoutExercise.getId(),
				exerciseMapper.toExerciseResponse(workoutExercise.getExercise()),
				workoutExercise.getSets(),
				workoutExercise.getReps(),
				workoutExercise.getWeight(),
				workoutExercise.getRpe(),
				workoutExercise.getOrderIndex(),
				workoutExercise.getNotes());

	}
}
