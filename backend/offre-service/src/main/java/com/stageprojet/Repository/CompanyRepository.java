package com.stageprojet.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stageprojet.model.Company;

import feign.Param;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    
    Optional<Company> findByName(String name);
    
    @Query("SELECT c FROM Company c JOIN FETCH c.assistantsRH WHERE c.id = :id")
    Optional<Company> findByIdWithAssistants(@Param("id") Long id);
}