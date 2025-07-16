package com.stageprojet.dto;

import lombok.Builder;
import lombok.Data;
import com.stageprojet.model.JwtToken;
import com.stageprojet.model.Role;

@Data
@Builder
public class AuthResponse {
	private Long userId;
    private String email;
    private String fullName;
    private Role role;
}
