package com.stageprojet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stageprojet.model.CV;

@Repository
public interface CVRepository extends JpaRepository<CV, Long> {
    Optional<CV> findByCandidatId(Long candidatId);
}
