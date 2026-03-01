package com.cursojava21.libraryapi.validator;

import com.cursojava21.libraryapi.exceptions.RegistroDuplicadoException;
import com.cursojava21.libraryapi.model.Autor;
import com.cursojava21.libraryapi.repository.AutorRepository;
import com.cursojava21.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AutorValidator {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Registro Duplicado");
        }
    }

    public boolean possuiLivroCadastrado(Autor autor) {
        if (livroRepository.existsByAutor(autor)) {
            return true;
        }
        return false;
    }

    private Boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorPersistido = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

        if (autor.getId() == null) {
            // Novo autor
            return autorPersistido.isPresent();
        } else {
            // Atualizando autor: retorna true se encontrar outro autor com mesmo nome/dados
            return autorPersistido.isPresent() && !autorPersistido.get().getId().equals(autor.getId());
        }
    }
}
