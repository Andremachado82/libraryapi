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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorResponseDTO> create(@Validated @RequestBody AutorRequestDTO requestDTO) {
        AutorResponseDTO response = autorService.create(requestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> getById(@PathVariable UUID id) {
        return autorService.getAutorPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = autorService.deletarPorId(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> search(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ) {
        List<AutorResponseDTO> autores = autorService.getAutores(nome, nacionalidade);
        return ResponseEntity.ok(autores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Validated @RequestBody AutorRequestDTO requestDTO) {
        boolean updated = autorService.atualizar(id, requestDTO);
        return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
