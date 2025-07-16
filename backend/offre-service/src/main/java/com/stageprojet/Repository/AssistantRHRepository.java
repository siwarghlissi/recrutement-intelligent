package com.stageprojet.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stageprojet.model.AssistantRH;

import feign.Param;

@Repository
public interface AssistantRHRepository extends JpaRepository<AssistantRH, Long> {
    
    Optional<AssistantRH> findByUserId(Long userId);
    
    List<AssistantRH> findByCompanyId(Long companyId);
 // Méthode pour trouver un assistant RH par email
    Optional<AssistantRH> findByEmail(String email);
    
    boolean existsByUserId(Long userId);
    
    @Query("SELECT a FROM AssistantRH a JOIN FETCH a.company WHERE a.userId = :userId")
    Optional<AssistantRH> findByUserIdWithCompany(@Param("userId") Long userId);
}