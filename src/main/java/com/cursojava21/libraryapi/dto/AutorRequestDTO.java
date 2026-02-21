package com.cursojava21.libraryapi.dto;

import java.time.LocalDate;

public record AutorRequestDTO(
    String nome,
    LocalDate dataNascimento,
    String nacionalidade
) {}
