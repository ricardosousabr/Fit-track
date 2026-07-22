package com.fittrack.user.mapper;

import com.fittrack.user.dto.UserResponse;
import com.fittrack.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
	public UserResponse toUserResponse(User user) {
		return new UserResponse(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getRole(),
				user.getActive()
		);
	}
}
