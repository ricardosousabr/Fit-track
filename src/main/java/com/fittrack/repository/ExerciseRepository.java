package com.fittrack.repository;

import com.fittrack.domain.ExerciseCategory;
import com.fittrack.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
	Optional<Exercise> findByName(String name);
	List<Exercise> findByCategory(ExerciseCategory category);
}
