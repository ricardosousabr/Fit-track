package com.fittrack.workout.controller;

import com.fittrack.workout.dto.WorkoutPlanRequest;
import com.fittrack.workout.dto.WorkoutPlanResponse;
import com.fittrack.workout.service.WorkoutPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workout-plans")
@RequiredArgsConstructor
public class WorkoutPlanController {
	
	private final WorkoutPlanService workoutPlanService;
	
	@PostMapping("/")
	public WorkoutPlanResponse createPlan(@RequestBody @Valid WorkoutPlanRequest request) {
		return workoutPlanService.create(request);
	}
	
	@GetMapping("/")
	public List<WorkoutPlanResponse> findAllPlans() {
		return workoutPlanService.findAll();
	}
	
	@GetMapping("/{id}")
	public WorkoutPlanResponse findPlan(@PathVariable UUID id) {
		return workoutPlanService.findById(id);
	}
	
	@PutMapping("/{id}")
	public WorkoutPlanResponse updatePlan(@PathVariable UUID id, @RequestBody @Valid WorkoutPlanRequest request) {
		return workoutPlanService.update(id, request);
	}
	
	@DeleteMapping("/{id}")
	public void deletePlain(@PathVariable UUID id) {
		workoutPlanService.deactivate(id);
	}
}
