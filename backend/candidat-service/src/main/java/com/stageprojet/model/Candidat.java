package com.stageprojet.model;

import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "candidats")
public class Candidat {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = true)
    private Long userId; // Référence à l'Auth Service
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false, unique = true)
    private String contactEmail;
    
    private String phone;
    
    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidature> candidatures = new ArrayList<>();
    
    @OneToOne(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private CV cv;
    
    // Méthode helper pour gérer la relation bidirectionnelle
    public void setCv(CV cv) {
        if (cv == null) {
            if (this.cv != null) {
                this.cv.setCandidat(null);
            }
        } else {
            cv.setCandidat(this);
        }
        this.cv = cv;
    }
}
