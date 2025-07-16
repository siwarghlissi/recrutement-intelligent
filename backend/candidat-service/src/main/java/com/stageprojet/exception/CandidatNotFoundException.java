package com.stageprojet.exception;

public class CandidatNotFoundException extends RuntimeException {
    public CandidatNotFoundException(Long id) {
        super("Candidat non trouvé avec l'ID: " + id);
    }
    
    public CandidatNotFoundException(String message) {
        super(message);
    }
}
