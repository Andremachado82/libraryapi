package com.cursojava21.libraryapi.services;

import com.cursojava21.libraryapi.repository.AutorRepository;
import com.cursojava21.libraryapi.dto.AutorRequestDTO;
import com.cursojava21.libraryapi.dto.AutorResponseDTO;
import com.cursojava21.libraryapi.mapper.AutorMapper;
import com.cursojava21.libraryapi.model.Autor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorMapper mapper;

    public AutorResponseDTO create(AutorRequestDTO autorRquestDTO) {
        Autor autor = mapper.toEntity(autorRquestDTO);

        Autor saved = autorRepository.save(autor);

        return mapper.toResponseDTO(saved);
    }

    public AutorResponseDTO getAutorPorId(UUID id) {
        Optional<Autor> autor = autorRepository.findById(id);
        return autor.map(mapper::toResponseDTO).orElse(null);
    }
}
