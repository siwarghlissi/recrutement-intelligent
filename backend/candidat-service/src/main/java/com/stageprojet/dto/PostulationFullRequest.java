package com.stageprojet.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class PostulationFullRequest {
    // Infos candidat (peuvent créer un nouveau candidat)
    @NotBlank
    private String fullName;
    
    @NotBlank
    @Email
    private String contactEmail;
    
    private String phone;
    
    
    @NotNull
    private Long offreId;
    
    
    
    @NotNull
    private MultipartFile cvFile;
}
