package com.cursojava21.libraryapi.controller;

import com.cursojava21.libraryapi.dto.AutorRequestDTO;
import com.cursojava21.libraryapi.dto.AutorResponseDTO;
import com.cursojava21.libraryapi.services.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorResponseDTO> create(@Validated @RequestBody AutorRequestDTO authorRequestDTO) {
        AutorResponseDTO response = autorService.create(authorRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorResponseDTO> getDetalhesAutorPorId(@PathVariable(value = "id") String idAutor) {
        var id = UUID.fromString(idAutor);
        AutorResponseDTO autorPorId = autorService.getAutorPorId(id);
        if (autorPorId != null) {
            return ResponseEntity.ok(autorPorId);
        }
        return ResponseEntity.notFound().build();
    }
}
