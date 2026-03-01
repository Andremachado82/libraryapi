package com.cursojava21.libraryapi.repository;

import com.cursojava21.libraryapi.model.Autor;
import com.cursojava21.libraryapi.model.GeneroLivroEnum;
import com.cursojava21.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void createLivro() {

        Autor autor = autorRepository.findFirstByOrderByIdAsc()
                .orElseGet(() -> {
                    Autor novo = new Autor();
                    novo.setNome("Julia Machado");
                    novo.setNacionalidade("brasileira");
                    novo.setDataNascimento(LocalDate.of(2007, 7, 19));
                    return autorRepository.save(novo);
                });

        Livro livro = new Livro();
        livro.setIsbn("wsfsdr2345fds");
        livro.setTitulo("Jaspion");
        livro.setGenero(GeneroLivroEnum.FANTASIA);
        livro.setPreco(new BigDecimal("13.00"));
        livro.setDataPublicacao(LocalDate.of(2000, 10, 1));
        livro.setAutor(autor);

        Livro livroSaved = livroRepository.save(livro);

        System.out.println("Livro ID: " + livroSaved.getId());
        System.out.println("Autor ID: " + livroSaved.getAutor().getId());
    }

    @Test
    public void updateLivro() {
        UUID uuid = UUID.fromString("2982c1d1-6422-429b-b699-2fd366d68661");

        Optional<Livro> livro = livroRepository.findById(uuid);

        if (livro.isPresent()) {
            Livro livroEncontrado = livro.get();
            System.out.println("Dados do Livro");
            System.out.println(livroEncontrado);

            livroEncontrado.setGenero(GeneroLivroEnum.FICCAO);
            livroRepository.save(livroEncontrado);
        }
    }

    @Test
    public void updateAutorLivro() {
        UUID idAutor = UUID.fromString("ecd7e46e-0047-4918-8f68-2f55bb284ed3");
        UUID idLivro = UUID.fromString("2982c1d1-6422-429b-b699-2fd366d68661");

        Optional<Autor> autor = autorRepository.findById(idAutor);

        Optional<Livro> livro = livroRepository.findById(idLivro);

        if (livro.isPresent() && autor.isPresent()) {
            livro.get().setAutor(autor.get());

            System.out.println("Dados do Livro");
            System.out.println(livro);

            livroRepository.save(livro.get());
        }
    }

    @Test
    public void getAllLivros() {
        List<Livro> livros = livroRepository.findAll();
        livros.forEach(System.out::println);
    }

    @Test
    public void getAllLivrosJpql() {
        List<Livro> livros = livroRepository.listarLivros();
        livros.forEach(System.out::println);
    }

    @Test
    public void getLivroById() {
        UUID id = UUID.fromString("2b33f309-3c09-40c2-95b1-e0c2354d11ed");
        Optional<Livro> livro = livroRepository.findById(id);
        livro.ifPresent(System.out::println);
    }

    @Test
    public void deleteLivroById() {
        UUID id = UUID.fromString("2b33f309-3c09-40c2-95b1-e0c2354d11ed");
        livroRepository.deleteById(id);
    }

    @Test
    public void deleteLivro() {
        UUID id = UUID.fromString("42de2f40-e04a-430b-89d4-b41e71ce6f9c");
        Livro livro = livroRepository.findById(id).get();
        livroRepository.delete(livro);
    }

    @Test
    void pesquisarLivroPorTitulo() {
        List<Livro> livros = livroRepository.findByTituloLikeIgnoreCase("harry%");
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisarLivroPorIsbn() {
        List<Livro> livros = livroRepository.findByIsbn("wsfsdr2345fds");
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisarLivroPorTituloAndPreco() {
        List<Livro> livros = livroRepository.findByTituloAndPreco("Harry potter", new BigDecimal("114.00"));
        livros.forEach(System.out::println);
    }
    @Test
    void pesquisarLivroPorTituloOrIsbn() {
        List<Livro> livros = livroRepository.findByTituloOrIsbn("Harry potter", "nbhgfngtfd");
        livros.forEach(System.out::println);
    }

}