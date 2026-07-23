package com.fittrack.workout.dto;

import com.fittrack.workout.domain.ExerciseCategory;

import java.util.UUID;

public record ExerciseResponse(UUID id, String name, ExerciseCategory category, String description) {
}
