package com.fittrack.workout.service;

import com.fittrack.user.domain.User;
import com.fittrack.user.repository.UserRepository;
import com.fittrack.workout.domain.WorkoutLog;
import com.fittrack.workout.domain.WorkoutPlan;
import com.fittrack.workout.domain.WorkoutSetLog;
import com.fittrack.workout.dto.WorkoutLogRequest;
import com.fittrack.workout.dto.WorkoutLogResponse;
import com.fittrack.workout.dto.WorkoutSetLogRequest;
import com.fittrack.workout.mapper.WorkoutLogMapper;
import com.fittrack.workout.repository.WorkoutExerciseRepository;
import com.fittrack.workout.repository.WorkoutLogRepository;
import com.fittrack.workout.repository.WorkoutPlanRepository;
import com.fittrack.workout.repository.WorkoutSetLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutLogService {
	private final WorkoutLogRepository workoutLogRepository;
	private final UserRepository userRepository;
	private final WorkoutPlanRepository workoutPlanRepository;
	private final WorkoutSetLogRepository workoutSetLogRepository;
	private final WorkoutExerciseRepository workoutExerciseRepository;
	private final WorkoutLogMapper workoutLogMapper;
	
	private User getUserAuthenticated() {
		UserDetails userAuthenticated = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByEmail(userAuthenticated.getUsername()).orElseThrow();
		
		return user;
	}
	
	private void checkOwnership(WorkoutPlan record, User user) {
		if (!record.getUser().getId().equals(user.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
		}
	}
	
	public WorkoutLogResponse logWorkout(UUID planId, WorkoutLogRequest request) {
		User user = getUserAuthenticated();
		WorkoutPlan plan = workoutPlanRepository.findById(planId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found"));
		checkOwnership(plan, user);
		
		WorkoutLog workoutLog = new WorkoutLog();
		workoutLog.setUser(user);
		workoutLog.setWorkoutPlan(plan);
		workoutLog.setNotes(request.notes());
		
		workoutLogRepository.save(workoutLog);
		
		for (WorkoutSetLogRequest setLogRequest : request.sets()) {
			WorkoutSetLog setLog = new WorkoutSetLog();
			setLog.setWorkoutLog(workoutLog);
			setLog.setSetNumber(setLogRequest.setNumber());
			setLog.setRepsDone(setLogRequest.repsDone());
			setLog.setWeightDone(setLogRequest.weightDone());
			setLog.setRpe(setLogRequest.rpe());
			setLog.setCompleted(setLogRequest.completed());
			
			workoutExerciseRepository.findById(setLogRequest.workoutExerciseId())
					.ifPresentOrElse(
							setLog::setWorkoutExercise,
							() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WorkoutExercise not found"); }
					);
			
			workoutSetLogRepository.save(setLog);
			
		}
		
		List<WorkoutSetLog> sets = workoutSetLogRepository.findByWorkoutLogId(workoutLog.getId());
		return workoutLogMapper.toWorkoutLogResponse(workoutLog, sets);
	}
	
	public List<WorkoutLogResponse> getWorkoutHistory(UUID planId) {
		User user = getUserAuthenticated();
		WorkoutPlan plan = workoutPlanRepository.findById(planId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found"));
		checkOwnership(plan, user);
		
		List<WorkoutLog> logs = workoutLogRepository.findByWorkoutPlanId(planId);
		
		return logs.stream().map(log -> {
			List<WorkoutSetLog> sets = workoutSetLogRepository.findByWorkoutLogId(log.getId());
			return workoutLogMapper.toWorkoutLogResponse(log, sets);
		}).toList();
		
	}
}
