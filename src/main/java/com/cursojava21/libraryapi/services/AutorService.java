package com.cursojava21.libraryapi.services;

import com.cursojava21.libraryapi.dto.AutorRequestDTO;
import com.cursojava21.libraryapi.dto.AutorResponseDTO;
import com.cursojava21.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.cursojava21.libraryapi.mappers.AutorMapper;
import com.cursojava21.libraryapi.model.Autor;
import com.cursojava21.libraryapi.repository.AutorRepository;
import com.cursojava21.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorMapper mapper;
    private final AutorValidator validator;

    public AutorResponseDTO create(AutorRequestDTO requestDTO) {
        Autor autor = mapper.toEntity(requestDTO);
        validator.validar(autor);
        Autor saved = autorRepository.save(autor);
        return mapper.toResponseDTO(saved);
    }

    public Optional<AutorResponseDTO> getAutorPorId(UUID id) {
        return autorRepository.findById(id).map(mapper::toResponseDTO);
    }

    public boolean deletarPorId(UUID id) {
        Optional<Autor> autorOpt = autorRepository.findById(id);
        if (autorOpt.isEmpty()) return false;

        Autor autor = autorOpt.get();
        if (validator.possuiLivroCadastrado(autor)) {
            throw new OperacaoNaoPermitidaException("Erro na exclusão: registro está sendo utilizado.");
        }

        autorRepository.delete(autor);
        return true;
    }

    public List<AutorResponseDTO> getAutores(String nome, String nacionalidade) {
        Autor probe = new Autor();
        probe.setNome(nome);
        probe.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Example<Autor> example = Example.of(probe, matcher);

        return autorRepository.findAll(example)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public boolean atualizar(UUID id, AutorRequestDTO requestDTO) {
        Optional<Autor> autorOpt = autorRepository.findById(id);
        if (autorOpt.isEmpty()) return false;

        Autor autor = autorOpt.get();
        autor.setNome(requestDTO.nome());
        autor.setNacionalidade(requestDTO.nacionalidade());
        autor.setDataNascimento(requestDTO.dataNascimento());
        validator.validar(autor);

        autorRepository.save(autor);
        return true;
    }
}
