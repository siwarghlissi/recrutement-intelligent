package com.stageprojet.dto;



import lombok.Data;

@Data
public class AssistantRHDTO {
    
    private Long userId;
    private Long companyId;
    
    private String email;
    private String fullName;
    
}
