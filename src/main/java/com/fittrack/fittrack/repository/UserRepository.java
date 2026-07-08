package com.fittrack.fittrack.repository;

import com.fittrack.fittrack.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
	Optional<Users> findByEmail(String email);
	Optional<Users> findByUsername(String username);
	Boolean existsByEmail(String email);
	Boolean existsByUsername(String username);
}
