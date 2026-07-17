package com.fittrack.fittrack.mapper;

import com.fittrack.fittrack.dto.response.UserResponse;
import com.fittrack.fittrack.entity.User;
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
