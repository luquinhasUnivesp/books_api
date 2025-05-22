package br.com.gesseff.books_api.repository;

import br.com.gesseff.books_api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> { // generics que recebe a entidade e o tipo do id

    List<Book> findByGenre(String genre); // método que retorna uma lista de livros de um gênero específico

    List<Book> findByAuthor(String author); // método que retorna uma lista de livros de um autor específico
}

