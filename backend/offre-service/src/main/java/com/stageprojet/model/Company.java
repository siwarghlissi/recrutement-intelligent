package com.stageprojet.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String address;
    
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonManagedReference // Pour la relation Company -> AssistantsRH
    private List<AssistantRH> assistantsRH = new ArrayList<>();
}
