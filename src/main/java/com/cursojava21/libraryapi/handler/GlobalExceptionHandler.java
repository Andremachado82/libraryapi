package com.cursojava21.libraryapi.handler;

import com.cursojava21.libraryapi.dto.ErrorResponse;
import com.cursojava21.libraryapi.dto.FieldErrorResponse;
import com.cursojava21.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.cursojava21.libraryapi.exceptions.RegistroDuplicadoException;
import com.cursojava21.libraryapi.exceptions.RegistroNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404
    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(RegistroNaoEncontradoException ex, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(Instant.now().toEpochMilli(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
                request.getRequestURI(), List.of()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // 422 - validação de campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleUnprocessableValidation(MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> new FieldErrorResponse(e.getField(), e.getDefaultMessage())).toList();

        ErrorResponse response = new ErrorResponse(Instant.now().toEpochMilli(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de Validação", request.getRequestURI(), errors
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    // 409 - conflito, registro duplicado
    @ExceptionHandler(RegistroDuplicadoException.class)
    public ResponseEntity<ErrorResponse> handleRegistroDuplicado(RegistroDuplicadoException ex, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(System.currentTimeMillis(), HttpStatus.CONFLICT.value(), ex.getMessage(),
                request.getRequestURI(), List.of() // array vazio
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // 400 - bad request
    @ExceptionHandler(OperacaoNaoPermitidaException.class) // exemplo de bad request
    public ResponseEntity<ErrorResponse> handleBadRequest(OperacaoNaoPermitidaException ex, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(Instant.now().toEpochMilli(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                request.getRequestURI(), List.of()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}