package com.cursojava21.libraryapi.repository;

import com.cursojava21.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void createAutor() {
        Autor autor = new Autor();
        autor.setNome("Julia Machado");
        autor.setNacionalidade("brasileira");
        autor.setDataNascimento(LocalDate.of(2007, 7, 19));
        autor.setLivros(new ArrayList<>());

        var autorSaved = autorRepository.save(autor);
        System.out.println(autorSaved);
    }

    @Test
    public void updateAutor() {
        UUID uuid = UUID.fromString("57f7bb26-be22-4805-ada5-c8559aaa11c7");

        Optional<Autor> autor = autorRepository.findById(uuid);

        if (autor.isPresent()) {
            Autor autorEncontrado = autor.get();
            System.out.println("Dados do Autor");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(2006, 7, 19));
            autorRepository.save(autorEncontrado);

        }
    }

    @Test
    public void getAllAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void getAutorById() {
        UUID id = UUID.fromString("03658e10-495f-466b-9027-75d5db17ba6a");
        Optional<Autor> autor = autorRepository.findById(id);
        autor.ifPresent(System.out::println);
    }

    @Test
    public void deleteAutorById() {
        UUID id = UUID.fromString("03658e10-495f-466b-9027-75d5db17ba6a");
        autorRepository.deleteById(id);
    }

    @Test
    public void deleteAutor() {
        UUID id = UUID.fromString("57f7bb26-be22-4805-ada5-c8559aaa11c7");
        Autor autor = autorRepository.findById(id).get();
        autorRepository.delete(autor);
    }

}