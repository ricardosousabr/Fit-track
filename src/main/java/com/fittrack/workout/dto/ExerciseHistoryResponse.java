package com.fittrack.workout.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ExerciseHistoryResponse(LocalDateTime loggedAt, String notes, List<SetHistoryResponse> sets) {
}
