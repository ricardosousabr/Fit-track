package com.fittrack.workout.dto;

import java.math.BigDecimal;

public record SetHistoryResponse(int setNumber, BigDecimal weightDone, int repsDone, BigDecimal rpe, boolean completed) {
}
