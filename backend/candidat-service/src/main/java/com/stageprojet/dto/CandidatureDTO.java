package com.stageprojet.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatureDTO {
    private Long id;
    private Long offreId;
    
    private String status;
    private LocalDateTime appliedAt;
    
    private CVDTO cv;
}
