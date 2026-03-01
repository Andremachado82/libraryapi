package com.cursojava21.libraryapi.dto;

import java.util.List;

public record ErrorResponse(

        Long timestamp,
        Integer status,
        String message,
        String path,
        List<FieldErrorResponse> errors
) {
}
