package com.fittrack.workout.dto;

import java.util.List;

public record WorkoutLogRequest(List<WorkoutSetLogRequest> sets, String notes) {
}
