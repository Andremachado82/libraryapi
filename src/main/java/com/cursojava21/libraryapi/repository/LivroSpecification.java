package com.cursojava21.libraryapi.repository;

import com.cursojava21.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.cursojava21.libraryapi.model.GeneroLivroEnum;
import com.cursojava21.libraryapi.model.Livro;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivroSpecification {

    public static Specification<Livro> comFiltros(String isbn,
            String titulo,
            String nomeAutor,
            String genero,
            String anoPublicacao
    ) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (isbn != null && !isbn.isBlank()) {
                predicates.add(cb.equal(root.get("isbn"), isbn));
            }

            if (titulo != null && !titulo.isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"
                ));
            }

            if (nomeAutor != null && !nomeAutor.isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("autor").get("nome")), "%" + nomeAutor.toLowerCase() + "%"
                ));
            }

            if (genero != null && !genero.isBlank()) {
                try {
                    GeneroLivroEnum generoEnum = GeneroLivroEnum.valueOf(genero.toUpperCase());
                    predicates.add(cb.equal(root.get("genero"), generoEnum));
                } catch (IllegalArgumentException e) {
                    throw new OperacaoNaoPermitidaException("Gênero inválido");
                }
            }

            if (anoPublicacao != null && !anoPublicacao.isBlank()) {
                try {
                    int ano = Integer.parseInt(anoPublicacao);
                    LocalDate inicio = LocalDate.of(ano, 1, 1);
                    LocalDate fim = LocalDate.of(ano, 12, 31);

                    predicates.add(cb.between(root.get("dataPublicacao"), inicio, fim));

                } catch (NumberFormatException e) {
                    throw new OperacaoNaoPermitidaException("Ano inválido");
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
