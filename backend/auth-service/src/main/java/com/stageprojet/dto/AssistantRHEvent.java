package com.stageprojet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AssistantRHEvent {
    private Long userId;
    private String email;
    private String fullName;
    private Long companyId; // Ajouté pour lier l'assistant RH à une entreprise
	
	}
