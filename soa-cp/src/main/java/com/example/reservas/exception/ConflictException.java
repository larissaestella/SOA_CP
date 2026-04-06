package com.example.reservas.exception;

/**
 * Exceção lançada quando ocorre um conflito de negócio, por exemplo, reservas sobrepostas.
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
