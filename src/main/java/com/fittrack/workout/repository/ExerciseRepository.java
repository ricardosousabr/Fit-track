package com.fittrack.workout.repository;

import com.fittrack.workout.domain.ExerciseCategory;
import com.fittrack.workout.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
	Optional<Exercise> findByName(String name);
	List<Exercise> findByCategory(ExerciseCategory category);
}
