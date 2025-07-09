package com.stageprojet.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class JwtToken {
    private String token;
    private String refreshToken;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
}