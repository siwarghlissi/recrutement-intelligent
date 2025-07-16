package com.stageprojet.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stageprojet.dto.CVDTO;
import com.stageprojet.dto.CandidatureDTO;
import com.stageprojet.dto.PostulationFullRequest;
import com.stageprojet.model.CV;
import com.stageprojet.model.Candidat;
import com.stageprojet.model.Candidature;
import com.stageprojet.repository.CVRepository;
import com.stageprojet.repository.CandidatRepository;
import com.stageprojet.repository.CandidatureRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidatureService {
    private final CandidatureRepository candidatureRepository;
    private final CandidatRepository candidatRepository;
    private final CVRepository cvRepository;
    private final KafkaProducerService kafkaProducerService;
    private final RestTemplate restTemplate;
    private final CVService cvService;
    
    @Value("${service.offres.url}")
    private String offresServiceUrl;
    
    public CandidatureDTO postuler(PostulationFullRequest request) throws IOException {
        
        Candidat candidat = candidatRepository.findByContactEmail(request.getContactEmail())
            .orElseGet(() -> {
                Candidat newCandidat = new Candidat();
                newCandidat.setFullName(request.getFullName());
                newCandidat.setContactEmail(request.getContactEmail());
                newCandidat.setPhone(request.getPhone());
                
                return candidatRepository.save(newCandidat);
            });

        // 2. Enregistrer le CV
        CV cv = cvService.saveCV(request.getCvFile(), candidat);
        
        kafkaProducerService.sendCvUploadEvent(cv.getId(), cv.getFilePath());

        // 3. Créer la candidature
        Candidature candidature = new Candidature();
        candidature.setOffreId(request.getOffreId());
        candidature.setCandidat(candidat);
        candidature.setCv(cv);
        
        candidature.setStatus("PENDING");
       
        candidature.setAppliedAt(LocalDateTime.now());
        

        return mapToDTO(candidatureRepository.save(candidature));
    }
    
    private CandidatureDTO mapToDTO(Candidature candidature) {
        CandidatureDTO dto = new CandidatureDTO();
        dto.setId(candidature.getId());
        dto.setOffreId(candidature.getOffreId());
        
        dto.setStatus(candidature.getStatus());
        dto.setAppliedAt(candidature.getAppliedAt());
        
        
        if (candidature.getCv() != null) {
            CVDTO cvDto = new CVDTO();
            cvDto.setId(candidature.getCv().getId());
            cvDto.setFilePath(candidature.getCv().getFilePath());
            cvDto.setStatus(candidature.getCv().getStatus());
            
            dto.setCv(cvDto);
        }
        
        return dto;
    }
    
    public Candidature getCandidatureById(Long id) {
        return candidatureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature non trouvée avec l'ID: " + id));
    }
}
