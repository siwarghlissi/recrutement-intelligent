package com.stageprojet.Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.stageprojet.Repository.AssistantRHRepository;
import com.stageprojet.Repository.CompanyRepository;
import com.stageprojet.dto.AssistantRHEvent;
import com.stageprojet.model.AssistantRH;
import com.stageprojet.model.Company;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

	private final AssistantRHRepository assistantRHRepository;
    private final CompanyRepository companyRepository;

    @KafkaListener(topics = "assistantRH.created", groupId = "offre-service-group")
    @Transactional
    public void handleAssistantRHCreated(AssistantRHEvent event) {
        // Vérifier si l'assistant RH existe déjà
    	if (assistantRHRepository.existsByUserId(event.getUserId())) {
            
            return;
        }
    	
    	// Vérifier si la compagnie existe
        Company company = companyRepository.findById(event.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + event.getCompanyId()));


    	 // Créer un nouvel assistant RH
        AssistantRH assistantRH = new AssistantRH();
        assistantRH.setUserId(event.getUserId());
        assistantRH.setEmail(event.getEmail());
        assistantRH.setFullName(event.getFullName());
        assistantRH.setCompany(company);

        // Sauvegarder
        assistantRHRepository.save(assistantRH);}
}