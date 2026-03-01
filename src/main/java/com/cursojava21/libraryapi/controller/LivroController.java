package com.cursojava21.libraryapi.controller;

import com.cursojava21.libraryapi.dto.LivroRequestDTO;
import com.cursojava21.libraryapi.dto.LivroResponseDTO;
import com.cursojava21.libraryapi.services.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroResponseDTO> create(@Validated @RequestBody LivroRequestDTO dto) {
        LivroResponseDTO response = livroService.create(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> getById(@PathVariable UUID id) {
        return livroService.getLivroById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<LivroResponseDTO>> search(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "nomeAutor", required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false) String genero,
            @RequestParam(value = "anoPublicacao", required = false) String anoPublicacao,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<LivroResponseDTO> livros = livroService.getByLivroWithParams(isbn, titulo, nomeAutor, genero, anoPublicacao, page,
                size
        );
        return ResponseEntity.ok(livros);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = livroService.deleteLivroById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Validated @RequestBody LivroRequestDTO dto) {
        boolean updated = livroService.atualizar(id, dto);
        return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
