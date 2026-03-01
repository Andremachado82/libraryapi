package com.cursojava21.libraryapi.services;

import com.cursojava21.libraryapi.dto.LivroRequestDTO;
import com.cursojava21.libraryapi.dto.LivroResponseDTO;
import com.cursojava21.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.cursojava21.libraryapi.exceptions.RegistroDuplicadoException;
import com.cursojava21.libraryapi.exceptions.RegistroNaoEncontradoException;
import com.cursojava21.libraryapi.mappers.LivroMapper;
import com.cursojava21.libraryapi.model.Autor;
import com.cursojava21.libraryapi.model.Livro;
import com.cursojava21.libraryapi.repository.AutorRepository;
import com.cursojava21.libraryapi.repository.LivroRepository;
import com.cursojava21.libraryapi.repository.LivroSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroMapper mapper;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public LivroResponseDTO create(LivroRequestDTO dto) {
        UUID idAutor = UUID.fromString(dto.idAutor());
        Autor autor = autorRepository.findById(idAutor)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Autor não encontrado: ID " + idAutor));

        if (livroRepository.existsByIsbn(dto.isbn())) {
            throw new RegistroDuplicadoException("ISBN duplicado: " + dto.isbn());
        }

        if (dto.dataPublicacao() != null && dto.dataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO && dto.preco() == null) {
            throw new OperacaoNaoPermitidaException("O campo 'preço' é obrigatório para livros publicados a partir de 2020.");
        }

        Livro livro = mapper.toEntity(dto);
        livro.setAutor(autor);

        Livro saved = livroRepository.save(livro);
        return mapper.toResponseDto(saved);
    }

    public Optional<LivroResponseDTO> getLivroById(UUID id) {
        return livroRepository.findById(id).map(mapper::toResponseDto);
    }

    public Page<LivroResponseDTO> getByLivroWithParams(
            String isbn,
            String titulo,
            String nomeAutor,
            String genero,
            String anoPublicacao,
            int page,
            int size
    ) {
        Specification<Livro> spec = LivroSpecification.comFiltros(isbn, titulo, nomeAutor, genero, anoPublicacao);
        Pageable pageable = PageRequest.of(page, size);

        return livroRepository.findAll(spec, pageable)
                .map(mapper::toResponseDto); // map mantém Page
    }

    public boolean deleteLivroById(UUID id) {
        Optional<Livro> livroOpt = livroRepository.findById(id);
        if (livroOpt.isEmpty()) return false;

        livroRepository.deleteById(id);
        return true;
    }

    public boolean atualizar(UUID id, LivroRequestDTO dto) {
        Optional<Livro> livroOpt = livroRepository.findById(id);
        if (livroOpt.isEmpty()) return false;

        Livro livro = livroOpt.get();
        livro.setTitulo(dto.titulo());
        livro.setIsbn(dto.isbn());
        livro.setDataPublicacao(dto.dataPublicacao());
        livro.setGenero(dto.genero());
        livro.setPreco(dto.preco());

        UUID idAutor = UUID.fromString(dto.idAutor());
        Autor autor = autorRepository.findById(idAutor)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Autor não encontrado: ID " + idAutor));
        livro.setAutor(autor);

        livroRepository.save(livro);
        return true;
    }
}
