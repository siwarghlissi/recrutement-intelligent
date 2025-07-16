package com.stageprojet.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stageprojet.dto.CandidatDTO;
import com.stageprojet.model.Candidat;
import com.stageprojet.repository.CandidatRepository;

import lombok.RequiredArgsConstructor;
import com.stageprojet.dto.CandidatureDTO;
import com.stageprojet.exception.CandidatNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class CandidatService {
    private final CandidatRepository candidatRepository;
    private final CVService cvService;
    private final KafkaProducerService kafkaProducerService;
    
    
    
    public CandidatDTO getCandidatById(Long id) {
        return candidatRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new CandidatNotFoundException(id));
    }
    
    public CandidatDTO getCandidatByUserId(Long userId) {
        return candidatRepository.findByUserId(userId)
                .map(this::mapToDTO)
                .orElseThrow(() -> new CandidatNotFoundException("User ID: " + userId));
    }
    
    private CandidatDTO mapToDTO(Candidat candidat) {
        CandidatDTO dto = new CandidatDTO();
        dto.setId(candidat.getId());
        dto.setUserId(candidat.getUserId());
        dto.setFullName(candidat.getFullName());
        dto.setContactEmail(candidat.getContactEmail());
        dto.setPhone(candidat.getPhone());
        
        if (candidat.getCv() != null) {
            dto.setCv(cvService.mapToDTO(candidat.getCv()));
        }
        
        if (candidat.getCandidatures() != null && !candidat.getCandidatures().isEmpty()) {
            dto.setCandidatures(candidat.getCandidatures().stream()
                    .map(c -> {
                        CandidatureDTO cdto = new CandidatureDTO();
                        cdto.setId(c.getId());
                        cdto.setOffreId(c.getOffreId());
                        
                        cdto.setStatus(c.getStatus());
                        cdto.setAppliedAt(c.getAppliedAt());
                        
                        if (c.getCv() != null) {
                            cdto.setCv(cvService.mapToDTO(c.getCv()));
                        }
                        return cdto;
                    })
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
}
