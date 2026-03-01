package com.cursojava21.libraryapi.exceptions;

import java.util.UUID;

public class RegistroNaoEncontradoException extends RuntimeException {

    public RegistroNaoEncontradoException(UUID id) {
        super("Registro com id " + id + " não encontrado");
    }

    public RegistroNaoEncontradoException(String message) {
        super(message);
    }
}
