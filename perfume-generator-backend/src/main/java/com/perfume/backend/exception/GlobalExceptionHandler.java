package com.perfume.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestion centralisée des exceptions de l'application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Erreurs métier (règles fonctionnelles).
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBusinessException(
            BusinessException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error", "BUSINESS_ERROR");
        response.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * Erreurs de validation (@Valid sur les DTO).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {

    Map<String, String> fields = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(err -> fields.put(err.getField(), err.getDefaultMessage()));

    Map<String, Object> response = new HashMap<>();
    response.put("error", "VALIDATION_ERROR");
    response.put("message", "Validation invalide");
    response.put("fields", fields);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
}

    /**
     * Erreurs inattendues (fallback).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(
            Exception ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error", "INTERNAL_SERVER_ERROR");
        response.put("message", "Une erreur interne est survenue.");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
