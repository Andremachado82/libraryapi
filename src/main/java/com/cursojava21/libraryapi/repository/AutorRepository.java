package com.cursojava21.libraryapi.repository;

import com.cursojava21.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    Optional<Autor> findFirstByOrderByIdAsc();

    List<Autor> findByNomeContainingIgnoreCaseAndNacionalidadeContainingIgnoreCase(String nome, String nacionaliadde);

    UUID id(UUID id);

    List<Autor> findByNomeContainingIgnoreCase(String nome);

    List<Autor> findByNacionalidadeContainingIgnoreCase(String nome);

    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(String nome, LocalDate DataNascimento, String nacionalidade);
}
