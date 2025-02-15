package br.com.gesseff.books_api.repository;

import br.com.gesseff.books_api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByGenre(String genre);

    List<Book> findByAuthor(String author);
}

