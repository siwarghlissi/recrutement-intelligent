package com.stageprojet.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stageprojet.dto.AuthResponse;
import com.stageprojet.dto.LoginRequest;
import com.stageprojet.dto.RegisterRequest;
import com.stageprojet.dto.UserInfoDto;
import com.stageprojet.exception.EmailAlreadyExistsException;
import com.stageprojet.exception.InvalidCredentialsException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import com.stageprojet.model.JwtToken;
import com.stageprojet.model.User;
import com.stageprojet.repository.UserRepository;
@Service
@RequiredArgsConstructor
public class AuthService {
 private final UserRepository userRepository;
 private final PasswordEncoder passwordEncoder;
 private final JwtService jwtService;
 
 public AuthResponse register(RegisterRequest request) {
     if (userRepository.existsByEmail(request.getEmail())) {
         throw new EmailAlreadyExistsException();
     }
     
     
     User user = User.builder()
             .email(request.getEmail())
             .password(passwordEncoder.encode(request.getPassword()))
             .fullName(request.getFullName())
             .role(request.getRole())
             .build();
     
     userRepository.save(user);
     
     return generateAuthResponse(user);
 }
 
 public AuthResponse login(LoginRequest request) {
     User user = userRepository.findByEmail(request.getEmail())
             .orElseThrow(InvalidCredentialsException::new);
     
     if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
         throw new InvalidCredentialsException();
     }
     
     return generateAuthResponse(user);
 }
 
 private AuthResponse generateAuthResponse(User user) {
     JwtToken token = jwtService.generateToken(user);
     
     return AuthResponse.builder()
             .token(token)
             .user(UserInfoDto.fromUser(user))
             .build();
 }
}

