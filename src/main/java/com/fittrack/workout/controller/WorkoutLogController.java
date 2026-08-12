package com.fittrack.workout.controller;

import com.fittrack.workout.dto.WorkoutLogRequest;
import com.fittrack.workout.dto.WorkoutLogResponse;
import com.fittrack.workout.service.WorkoutLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workout-plans")
@RequiredArgsConstructor
public class WorkoutLogController {
	private final WorkoutLogService workoutLogService;
	
	@PostMapping("/{planId}/logs")
	public WorkoutLogResponse logWorkout(@PathVariable UUID planId, @RequestBody @Valid WorkoutLogRequest request) {
		return workoutLogService.logWorkout(planId, request);
	}
	
	@GetMapping("/{planId}/logs")
	public List<WorkoutLogResponse> getWorkoutHistory(@PathVariable UUID planId) {
		return workoutLogService.getWorkoutHistory(planId);
	}
}
