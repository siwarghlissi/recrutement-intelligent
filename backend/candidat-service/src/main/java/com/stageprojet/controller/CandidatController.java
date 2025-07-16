package com.stageprojet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stageprojet.dto.CandidatDTO;
import com.stageprojet.service.CandidatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/candidats")
@RequiredArgsConstructor
public class CandidatController {
    private final CandidatService candidatService;
    
    @GetMapping("/{id}")
    public ResponseEntity<CandidatDTO> getCandidatById(@PathVariable Long id) {
        return ResponseEntity.ok(candidatService.getCandidatById(id));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<CandidatDTO> getCandidatByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(candidatService.getCandidatByUserId(userId));
    }
    
    
}
