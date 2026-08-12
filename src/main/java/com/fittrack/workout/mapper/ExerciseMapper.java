package com.fittrack.workout.mapper;

import com.fittrack.workout.domain.Exercise;
import com.fittrack.workout.dto.ExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExerciseMapper {
	
	public ExerciseResponse toExerciseResponse(Exercise exercise) {
		return new ExerciseResponse(
				exercise.getId(),
				exercise.getName(),
				exercise.getCategory(),
				exercise.getDescription());
	}
}
