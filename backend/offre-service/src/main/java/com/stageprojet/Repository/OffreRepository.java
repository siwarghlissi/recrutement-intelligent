package com.stageprojet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stageprojet.model.Offre;

import feign.Param;

@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {
    
    @Query("SELECT o FROM Offre o WHERE " +
           "LOWER(o.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Offre> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(@Param("keyword") String keyword);
    
    List<Offre> findByStatus(String status);
    
    List<Offre> findByCompanyId(Long companyId);
    
    List<Offre> findByAssistantRH_UserId(Long userId);
}
