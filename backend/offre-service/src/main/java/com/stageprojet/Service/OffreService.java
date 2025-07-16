package com.stageprojet.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.stageprojet.Repository.AssistantRHRepository;
import com.stageprojet.Repository.CompanyRepository;
import com.stageprojet.Repository.OffreRepository;
import com.stageprojet.dto.OffreDTO;
import com.stageprojet.model.AssistantRH;
import com.stageprojet.model.Company;
import com.stageprojet.model.Offre;
import com.stageprojet.exception.ResourceNotFoundException;
import com.stageprojet.exception.UnauthorizedException;

import java.util.List;

@Service
@Transactional
public class OffreService {
    
    private final OffreRepository offreRepository;
    private final CompanyRepository companyRepository;
    private final AssistantRHRepository assistantRHRepository;
    private final RestTemplate restTemplate;
    
    @Value("${AUTH_SERVICE_URL}")
    private String authServiceUrl;

    @Autowired
    public OffreService(OffreRepository offreRepository,
                       CompanyRepository companyRepository,
                       AssistantRHRepository assistantRHRepository,
                       RestTemplate restTemplate) {
        this.offreRepository = offreRepository;
        this.companyRepository = companyRepository;
        this.assistantRHRepository = assistantRHRepository;
        this.restTemplate = restTemplate;
    }
    
    public List<Offre> getAllOffres() {
        return offreRepository.findAll();
    }
    
    public Offre getOffreById(Long id) {
        return offreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offre not found with id: " + id));
    }
    
    public List<Offre> searchOffres(String keyword) {
        return offreRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword);
    }
    
    public Offre createOffre(OffreDTO offreDTO) {
        // plus de validation RH ici

        Company company = companyRepository.findById(offreDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + offreDTO.getCompanyId()));

        AssistantRH assistantRH = assistantRHRepository.findById(offreDTO.getAssistantRHId())
                .orElseThrow(() -> new ResourceNotFoundException("AssistantRH not found with id: " + offreDTO.getAssistantRHId()));

        Offre offre = mapToEntity(offreDTO, company, assistantRH);
        assistantRH.addOffre(offre);
        return offreRepository.save(offre);
    }
    
    public Offre updateOffre(Long id, OffreDTO offreDTO) {
        Offre existingOffre = getOffreById(id);

        updateEntityFromDTO(existingOffre, offreDTO);
        return offreRepository.save(existingOffre);
    }

    public void deleteOffre(Long id) {
        Offre offre = getOffreById(id);
        offreRepository.delete(offre);
    }
    
    
    
    
    
    private Offre mapToEntity(OffreDTO dto, Company company, AssistantRH assistantRH) {
        Offre offre = new Offre();
        offre.setTitle(dto.getTitle());
        offre.setDescription(dto.getDescription());
        offre.setLocation(dto.getLocation());
        offre.setContractType(dto.getContractType());
        offre.setRequiredSkills(dto.getRequiredSkills());
        offre.setExperienceYears(dto.getExperienceYears());
        offre.setStatus(dto.getStatus());
        offre.setCompany(company);
        offre.setAssistantRH(assistantRH);
        return offre;
    }
    
    private void updateEntityFromDTO(Offre offre, OffreDTO dto) {
        offre.setTitle(dto.getTitle());
        offre.setDescription(dto.getDescription());
        offre.setLocation(dto.getLocation());
        offre.setContractType(dto.getContractType());
        offre.setRequiredSkills(dto.getRequiredSkills());
        offre.setExperienceYears(dto.getExperienceYears());
        offre.setStatus(dto.getStatus());
    }
}