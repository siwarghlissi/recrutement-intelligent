package com.stageprojet.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    
    @Value("${kafka.topic.cv-uploaded}")
    private String cvUploadedTopic;

    
    public void sendCvUploadEvent(Long cvId, String filePath) {
        try {
            ObjectNode message = objectMapper.createObjectNode();
            message.put("cvId", cvId);
            message.put("filePath", filePath);
            
            kafkaTemplate.send(cvUploadedTopic, objectMapper.writeValueAsString(message));
            log.info("Message Kafka envoyé - Topic: {} | CV ID: {}", cvUploadedTopic, cvId);
        } catch (Exception e) {
            log.error("Erreur Kafka - CV ID: {}", cvId, e);
            throw new RuntimeException("Erreur d'envoi Kafka", e);
        }
    }
}
