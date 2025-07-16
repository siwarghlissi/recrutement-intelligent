package com.stageprojet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stageprojet.model.Candidat;
@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long> {
    Optional<Candidat> findByUserId(Long userId);
    Optional<Candidat> findByContactEmail(String email);
}
