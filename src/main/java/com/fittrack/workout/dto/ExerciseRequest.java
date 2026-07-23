package com.fittrack.workout.dto;

import com.fittrack.workout.domain.ExerciseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExerciseRequest(@NotBlank(message = "Name is required") String name, @NotNull(message = "Category id required") ExerciseCategory category, String description) {
}
