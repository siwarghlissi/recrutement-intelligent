package com.stageprojet.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CV {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String filePath; // Chemin dans MinIO
    private String status; // "UPLOADED", "PROCESSING", "PROCESSED", "ERROR"
    
    @OneToOne
    @JoinColumn(name = "candidat_id")
    private Candidat candidat;
}
