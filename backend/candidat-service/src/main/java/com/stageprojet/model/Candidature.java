package com.stageprojet.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Candidature {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long offreId;
    private String status = "PENDING"; // Simplifié en String
    
    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private Candidat candidat;
    
    @OneToOne
    @JoinColumn(name = "cv_id")
    private CV cv;
    
    private LocalDateTime appliedAt = LocalDateTime.now();
}
