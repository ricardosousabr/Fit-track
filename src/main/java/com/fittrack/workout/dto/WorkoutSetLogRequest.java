package com.fittrack.workout.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WorkoutSetLogRequest(UUID workoutExerciseId, int setNumber, int repsDone, BigDecimal weightDone, BigDecimal rpe, Boolean completed) {
}
