package com.fittrack.workout.dto;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record WorkoutExerciseRequest(@Min(1) int sets, @Min(1) int reps, BigDecimal weight, BigDecimal rpe, int orderIndex, String notes) {
}
