package com.stageprojet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stageprojet.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
