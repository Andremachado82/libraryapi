package com.cursojava21.libraryapi.repository;

import com.cursojava21.libraryapi.model.Autor;
import com.cursojava21.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    boolean existsByIsbn(String isbn);

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTituloLikeIgnoreCase(String titulo);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn(String isbn);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    @Query("select l from Livro as l order by l.titulo")
    List<Livro> listarLivros();

    @Query("select l from Livro as l where l.genero = :genero")
    List<Livro> listarLivrosPrGenero(@Param("genero") String genero);

    @Transactional
    @Modifying
    @Query("delete from Livro where genero = :genero")
    List<Livro> deleteLivroPorGenero(@Param("genero") String genero);

    boolean existsByAutor(Autor autor);
}
