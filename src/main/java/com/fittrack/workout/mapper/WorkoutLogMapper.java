package com.fittrack.workout.mapper;

import com.fittrack.workout.domain.WorkoutLog;
import com.fittrack.workout.domain.WorkoutSetLog;
import com.fittrack.workout.dto.WorkoutLogResponse;
import com.fittrack.workout.dto.WorkoutSetLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkoutLogMapper {
	private final WorkoutPlanMapper workoutPlanMapper;
	
	public WorkoutLogResponse toWorkoutLogResponse(WorkoutLog workoutLog, List<WorkoutSetLog> sets) {
		return new WorkoutLogResponse(
				workoutLog.getId(),
				workoutPlanMapper.toWorkoutPlanResponse(workoutLog.getWorkoutPlan()),
				workoutLog.getLoggedAt(),
				workoutLog.getNotes(),
				sets.stream().map(this::toWorkoutSetLogResponse).toList()
		);
	}
	
	private WorkoutSetLogResponse toWorkoutSetLogResponse(WorkoutSetLog set) {
		return new WorkoutSetLogResponse(
				set.getId(),
				set.getSetNumber(),
				set.getRepsDone(),
				set.getWeightDone(),
				set.getRpe(),
				set.getCompleted()
				
		);
	}
}
