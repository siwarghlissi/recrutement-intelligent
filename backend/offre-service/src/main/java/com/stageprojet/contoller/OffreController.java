package com.stageprojet.contoller;


import com.stageprojet.Service.OffreService;
import com.stageprojet.dto.OffreDTO;
import com.stageprojet.model.Offre;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offres")
public class OffreController {

    private final OffreService offreService;

    public OffreController(OffreService offreService) {
        this.offreService = offreService;
    }

    @GetMapping
    public ResponseEntity<List<Offre>> getAllOffres() {
        return ResponseEntity.ok(offreService.getAllOffres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offre> getOffreById(@PathVariable Long id) {
        return ResponseEntity.ok(offreService.getOffreById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Offre>> searchOffres(@RequestParam String keyword) {
        return ResponseEntity.ok(offreService.searchOffres(keyword));
    }

    @PostMapping
    public ResponseEntity<Offre> createOffre(@RequestBody OffreDTO offreDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(offreService.createOffre(offreDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offre> updateOffre(@PathVariable Long id, @RequestBody OffreDTO offreDTO) {
        return ResponseEntity.ok(offreService.updateOffre(id, offreDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable Long id) {
        offreService.deleteOffre(id);
        return ResponseEntity.noContent().build();
    }
}
