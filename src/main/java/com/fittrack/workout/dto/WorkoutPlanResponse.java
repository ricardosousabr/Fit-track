package com.fittrack.workout.dto;

import java.time.LocalDate;
import java.util.UUID;

public record WorkoutPlanResponse(UUID id, String name, String description, LocalDate startDate, LocalDate endDate, boolean active) {
}
