package com.stageprojet.service;

import com.stageprojet.dto.*;
import com.stageprojet.exception.EmailAlreadyExistsException;
import com.stageprojet.model.Role;
import com.stageprojet.model.User;
import com.stageprojet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate; // Injection du KafkaTemplate

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword()) // À remplacer par un passwordEncoder plus tard
                .fullName(request.getFullName())
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);

        // Publication sur Kafka si c'est un assistant RH
        if (request.getRole() == Role.ASSISTANT_RH) {
            AssistantRHEvent event = AssistantRHEvent.builder()
                    .userId(savedUser.getId())
                    .email(savedUser.getEmail())
                    .fullName(savedUser.getFullName())
                    .companyId(request.getCompanyId())
                    .build();
            
            kafkaTemplate.send("assistantRH.created", event);
        }

        return AuthResponse.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .role(savedUser.getRole())
                .build();
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }
}