package com.stageprojet.dto;

import lombok.Builder;
import lombok.Data;
import com.stageprojet.model.JwtToken;

@Data
@Builder
public class AuthResponse {
    private JwtToken token;
    private UserInfoDto user;
}
