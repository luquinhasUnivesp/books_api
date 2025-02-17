package br.com.gesseff.books_api.repository;

import br.com.gesseff.books_api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByGenre(String genre);

    List<Book> findByAuthor(String author);
}

