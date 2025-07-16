package com.stageprojet.model;



import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "assistants_rh")
public class AssistantRH {
    
    @Id
    private Long userId; // Liaison avec le service Auth
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonBackReference // Empêche la sérialisation de cette propriété
    private Company company;
    
    
    @Column(unique = true, nullable = false)
    private String email;
    
    
    
    private String fullName;
    
    @OneToMany(mappedBy = "assistantRH", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference // Pour la relation AssistantRH -> Offres
    private List<Offre> offres = new ArrayList<>();
    
    // Méthodes utilitaires pour synchroniser la relation bidirectionnelle
    public void addOffre(Offre offre) {
        offres.add(offre);
        offre.setAssistantRH(this);
    }
    
    public void removeOffre(Offre offre) {
        offres.remove(offre);
        offre.setAssistantRH(null);
    }
}