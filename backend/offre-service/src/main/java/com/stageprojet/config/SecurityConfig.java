package com.stageprojet.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.client.RestTemplate;

@Configuration

public class SecurityConfig {
    
	@Value("${AUTH_SERVICE_URL:http://localhost:8081}")
	private String authServiceUrl;

    
    private final RestTemplate restTemplate;

    // Injection par constructeur
    public SecurityConfig(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
} 