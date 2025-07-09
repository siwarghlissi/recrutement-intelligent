package com.stageprojet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stageprojet.dto.AuthResponse;
import com.stageprojet.dto.LoginRequest;
import com.stageprojet.dto.RegisterRequest;
import com.stageprojet.dto.UserInfoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.stageprojet.service.AuthService;

//AuthController.java
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
 private final AuthService authService;
 
 @PostMapping("/register")
 public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
     return ResponseEntity.ok(authService.register(request));
 }
 
 @PostMapping("/login")
 public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
     return ResponseEntity.ok(authService.login(request));
 }

}
