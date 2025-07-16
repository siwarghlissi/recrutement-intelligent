package com.stageprojet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stageprojet.dto.CVDTO;
import com.stageprojet.service.CVService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cvs")
@RequiredArgsConstructor
public class CVController {
    private final CVService cvService;
    
    @GetMapping("/{id}")
    public ResponseEntity<CVDTO> getCVById(@PathVariable Long id) {
        return ResponseEntity.ok(cvService.getCVById(id));
    }
    
    @GetMapping("/candidat/{candidatId}")
    public ResponseEntity<CVDTO> getCVByCandidatId(@PathVariable Long candidatId) {
        return ResponseEntity.ok(cvService.getCVByCandidatId(candidatId));
    }
    
    
}
