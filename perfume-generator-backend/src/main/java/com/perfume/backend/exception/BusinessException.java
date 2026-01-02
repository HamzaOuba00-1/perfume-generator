package com.perfume.backend.exception;

/**
 * Exception métier levée lorsque
 * une règle fonctionnelle est violée.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
