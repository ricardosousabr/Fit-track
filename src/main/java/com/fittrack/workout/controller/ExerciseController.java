package com.fittrack.workout.controller;

import com.fittrack.workout.domain.ExerciseCategory;
import com.fittrack.workout.dto.ExerciseRequest;
import com.fittrack.workout.dto.ExerciseResponse;
import com.fittrack.workout.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {
	private final ExerciseService exerciseService;
	
	@PostMapping("/")
	public ExerciseResponse create(@RequestBody @Valid ExerciseRequest request) {
		return exerciseService.createExercise(request);
	}
	
	@GetMapping("/")
	public List<ExerciseResponse> listExercise() {
		return exerciseService.findAllExercises();
	}
	
	@GetMapping("/{id}")
	public ExerciseResponse findExercise(@PathVariable UUID id){
		return exerciseService.findExercise(id);
	}
	
	@GetMapping("/category/{category}")
	public List<ExerciseResponse> findCategory(@PathVariable ExerciseCategory category) {
		return exerciseService.findByCategory(category);
	}
	
}
