package com.cursojava21.libraryapi.dto;

public record FieldErrorResponse(
        String field,
        String error
) {
}
