package com.fittrack.workout.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record WorkoutPlanRequest(@NotBlank(message = "Name is required") String recordName, String description, @NotNull(message = "Start date is required") LocalDate startDate, LocalDate endDate) {
}
