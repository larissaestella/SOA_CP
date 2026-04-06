package com.example.reservas.exception;

/**
 * Exceção lançada quando dados de entrada são inválidos.
 */
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}
