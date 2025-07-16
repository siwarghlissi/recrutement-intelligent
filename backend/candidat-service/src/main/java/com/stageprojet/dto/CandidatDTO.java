package com.stageprojet.dto;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatDTO {
    private Long id;
    private Long userId;
    private String fullName;
    private String contactEmail;
    private String phone;
    private List<CandidatureDTO> candidatures;
    private CVDTO cv;
}