package com.stageprojet.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stageprojet.dto.CandidatureDTO;
import com.stageprojet.dto.PostulationFullRequest;
import com.stageprojet.model.Candidature;
import com.stageprojet.service.CVService;
import com.stageprojet.service.CandidatureService;

import org.springframework.core.io.Resource; 
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/candidatures")
@RequiredArgsConstructor
public class CandidatureController {
    private final CandidatureService candidatureService;
    private final CVService cvService;
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CandidatureDTO> postuler(
        @ModelAttribute PostulationFullRequest request
    ) throws IOException {
        return ResponseEntity.ok(candidatureService.postuler(request));
    }
    
    @GetMapping("/{id}/cv")
    public ResponseEntity<Resource> downloadCV(@PathVariable Long id) {
        Candidature candidature = candidatureService.getCandidatureById(id);
        Resource resource = cvService.getCVFile(candidature.getCv().getId());
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + candidature.getCv().getFilePath() + "\"")
                .body(resource);
    }
}
