package com.cursojava21.libraryapi.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AutorRquestDTO {

    @NonNull
    private String nome;

    @NonNull
    private LocalDate dataNascimento;

    @NonNull
    private String nacionalidade;
}
