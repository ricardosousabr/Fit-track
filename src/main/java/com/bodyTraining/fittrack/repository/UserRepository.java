package com.bodyTraining.fittrack.repository;

import com.bodyTraining.fittrack.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByEmail(String email);
	Optional<Users> findByUsername(String username);
	Boolean existsByEmail(String email);
	Boolean existsByUsername(String username);
}
