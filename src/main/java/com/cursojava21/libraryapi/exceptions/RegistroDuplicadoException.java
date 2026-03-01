package com.cursojava21.libraryapi.exceptions;

public class RegistroDuplicadoException extends RuntimeException {
    public RegistroDuplicadoException() {
        super();
    }

    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
