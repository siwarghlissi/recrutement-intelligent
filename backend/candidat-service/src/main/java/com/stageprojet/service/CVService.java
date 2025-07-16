package com.stageprojet.service;

//Spring Framework Imports
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//MinIO Client Imports
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

//Java I/O Imports
import java.io.IOException;
import java.io.InputStream;

import com.stageprojet.dto.CVDTO;
//Project Model/Repository Imports
import com.stageprojet.model.CV;
import com.stageprojet.model.Candidat;
import com.stageprojet.repository.CVRepository;

//Utility Imports
import org.springframework.util.StringUtils; // Pour cleanPath()

//Lombok Imports
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Service
@RequiredArgsConstructor
@Slf4j
public class CVService {
    private final CVRepository cvRepository;
    private final MinioClient minioClient;
    
    @Value("${minio.bucket.name}")
    private String bucketName;
    
    public CV saveCV(MultipartFile file, Candidat candidat) throws IOException {
        // Vérifier si le candidat a déjà un CV
        cvRepository.findByCandidatId(candidat.getId()).ifPresent(cv -> {
            try {
                // Supprimer l'ancien fichier dans MinIO
                minioClient.removeObject(
                    RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(cv.getFilePath())
                        .build());
                // Supprimer l'entrée en base
                cvRepository.delete(cv);
            } catch (Exception e) {
                log.error("Erreur lors de la suppression de l'ancien CV", e);
            }
        });
        
        // Générer un nom de fichier unique
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = "cv_" + candidat.getId() + "_" + System.currentTimeMillis() + fileExtension;
        
        // Enregistrer dans MinIO
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(newFilename)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du CV dans MinIO", e);
        }
        
        // Enregistrer les métadonnées en base
        CV cv = new CV();
        cv.setFilePath(newFilename);
        cv.setStatus("UPLOADED"); // Statut simple sans enum
        cv.setCandidat(candidat);
        
        return cvRepository.save(cv);
    }
    
    public Resource getCVFile(Long cvId) {
        CV cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new RuntimeException("CV non trouvé"));
        
        try {
            InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(cv.getFilePath())
                    .build());
            
            return new InputStreamResource(stream);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du fichier CV", e);
        }
    }
    
    public CVDTO getCVById(Long id) {
        CV cv = cvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CV non trouvé avec l'ID: " + id));
        return mapToDTO(cv);
    }

    public CVDTO getCVByCandidatId(Long candidatId) {
        CV cv = cvRepository.findByCandidatId(candidatId)
                .orElseThrow(() -> new RuntimeException("CV non trouvé pour le candidat ID: " + candidatId));
        return mapToDTO(cv);
    }
    public CVDTO mapToDTO(CV cv) {
        if (cv == null) {
            return null;
        }
        
        CVDTO dto = new CVDTO();
        dto.setId(cv.getId());
        dto.setFilePath(cv.getFilePath());
        dto.setStatus(cv.getStatus());
        
        return dto;
    }
}