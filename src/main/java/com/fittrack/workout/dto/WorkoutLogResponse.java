package com.fittrack.workout.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record WorkoutLogResponse(UUID sessionId, WorkoutPlanResponse plan, LocalDateTime logetdAt, String notes, List<WorkoutSetLogResponse> sets) {
}
