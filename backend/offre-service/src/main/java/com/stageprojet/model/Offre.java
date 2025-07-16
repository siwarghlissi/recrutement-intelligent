package com.stageprojet.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
@Table(name = "offres")
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assistant_rh_id")
    @JsonIgnore // Empêche la sérialisation de cette propriété
    private AssistantRH assistantRH;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnoreProperties("offres")
    private Company company;

    
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String location;
    private String contractType; // CDI, CDD, STAGE, FREELANCE, ALTERNANCE
    private String requiredSkills;
    private Integer experienceYears;
    private String status; // DRAFT, PUBLISHED, PAUSED, CLOSED
    
    
   
}