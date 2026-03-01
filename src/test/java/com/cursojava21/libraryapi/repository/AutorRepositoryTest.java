package com.cursojava21.libraryapi.repository;

import com.cursojava21.libraryapi.model.Autor;
import com.cursojava21.libraryapi.model.GeneroLivroEnum;
import com.cursojava21.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

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
    @Transactional
    public void getAutorById() {
        UUID id = UUID.fromString("1459e923-326a-433a-a28f-37c674b91679");
        Optional<Autor> autor = autorRepository.findById(id);
        autor.ifPresent(System.out::println);
        autor.get().getLivros().forEach(System.out::println);
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

    @Test
    public void createAutorComLivros() {
        Autor autor = new Autor();
        autor.setNome("Fernanda Machado");
        autor.setNacionalidade("brasileira");
        autor.setDataNascimento(LocalDate.of(1988, 7, 19));

        Livro livro = new Livro();
        livro.setIsbn("wsfsdr2345fds");
        livro.setTitulo("Harry potter");
        livro.setGenero(GeneroLivroEnum.FANTASIA);
        livro.setPreco(new BigDecimal("114.00"));
        livro.setDataPublicacao(LocalDate.of(1999, 10, 1));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("nbhgfngtfd");
        livro2.setTitulo("Joao e maria");
        livro2.setGenero(GeneroLivroEnum.FANTASIA);
        livro2.setPreco(new BigDecimal("110.00"));
        livro2.setDataPublicacao(LocalDate.of(2000, 10, 1));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        var autorSaved = autorRepository.save(autor);
        System.out.println(autorSaved);
    }

    @Test
    void listarLivrosAutor() {
        UUID id = UUID.fromString("1459e923-326a-433a-a28f-37c674b91679");
        Autor autor = autorRepository.findById(id).get();

        List<Livro> livroByAutor = livroRepository.findByAutor(autor);
        autor.setLivros(livroByAutor);
        autor.getLivros().forEach(System.out::println);
    }

}