package com.fittrack.workout.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WorkoutSetLogResponse(UUID id, int setNumber, int repsDone, BigDecimal weightDone, BigDecimal rpe, Boolean completed) {
}
