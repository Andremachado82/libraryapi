package com.cursojava21.libraryapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LivroResponseDTO(

        String id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        String genero,
        BigDecimal preco,
        AutorRequestDTO autor
) {
}
