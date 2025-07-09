package com.stageprojet.dto;

import lombok.Builder;

import com.stageprojet.model.User;

import lombok.*;
import lombok.Data;
import com.stageprojet.model.Role;

@Data
@Builder
public class UserInfoDto {
    private Long id;
    private String email;
    private String fullName;
    private Role role;
    
    public static UserInfoDto fromUser(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }
    
}
