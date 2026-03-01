package com.cursojava21.libraryapi.dto;

import com.cursojava21.libraryapi.model.GeneroLivroEnum;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LivroRequestDTO(

        @ISBN
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 20)
        String isbn,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 2, max = 150)
        String titulo,

        @NotNull(message = "Campo obrigatório")
        @Past(message = "não pode ser uma data futura")
        LocalDate dataPublicacao,

        GeneroLivroEnum genero,

        @DecimalMin(value = "0.00", inclusive = true, message = "Valor deve ser igual ou maior que zero")
        @Digits(integer = 16, fraction = 2, message = "Valor fora do limite de dígitos")
        BigDecimal preco,

        @NotBlank(message = "Campo obrigatório")
        String idAutor
) {
}
