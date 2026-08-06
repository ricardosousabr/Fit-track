package com.fittrack.workout.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WorkoutExerciseResponse(UUID id, ExerciseResponse exercise, int sets, int reps, BigDecimal weight, BigDecimal rpe, int orderIndex, String notes) {
}
