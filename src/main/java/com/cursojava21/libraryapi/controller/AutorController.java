package com.cursojava21.libraryapi.controller;

import com.cursojava21.libraryapi.dto.AutorRequestDTO;
import com.cursojava21.libraryapi.dto.AutorResponseDTO;
import com.cursojava21.libraryapi.services.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorResponseDTO> create(@Validated @RequestBody AutorRequestDTO authorRequestDTO) {
        AutorResponseDTO response = autorService.create(authorRequestDTO);
        return ResponseEntity.created(URI.create("/autores/" + response.getId())).body(response);
    }
}
