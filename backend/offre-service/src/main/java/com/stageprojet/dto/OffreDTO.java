package com.stageprojet.dto;

import com.stageprojet.model.AssistantRH;
import com.stageprojet.model.Company;
import com.stageprojet.model.Offre;

import lombok.Data;

@Data
public class OffreDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String contractType;
    private String requiredSkills;
    private Integer experienceYears;
    private String status;
    private Long companyId;
    private Long assistantRHId;
    private String companyName;
    
    // Getters and Setters
}
