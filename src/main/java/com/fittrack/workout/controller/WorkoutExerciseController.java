package com.fittrack.workout.controller;

import com.fittrack.workout.dto.WorkoutExerciseRequest;
import com.fittrack.workout.dto.WorkoutExerciseResponse;
import com.fittrack.workout.service.WorkoutExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("workout-plans")
@RequiredArgsConstructor
public class WorkoutExerciseController {
	private final WorkoutExerciseService exerciseService;
	
	@PostMapping("/{planId}/exercises/{exerciseId}")
	public WorkoutExerciseResponse addExercise(@PathVariable UUID planId, @PathVariable UUID exerciseId, @RequestBody @Valid WorkoutExerciseRequest request) {
		return  exerciseService.addExercise(planId, exerciseId, request);
	}
	
	@DeleteMapping("/{planId}/exercises/{exerciseId}")
	public void removeExercise(@PathVariable UUID planId, @PathVariable UUID exerciseId) {
		exerciseService.removeExercise(planId, exerciseId);
	}
	
	@GetMapping("/{planId}/exercises")
	public List<WorkoutExerciseResponse> listExercises(@PathVariable UUID planId) {
		return exerciseService.listExercises(planId);
	}
}
