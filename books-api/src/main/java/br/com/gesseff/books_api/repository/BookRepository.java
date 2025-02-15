package br.com.gesseff.books_api.repository;

import br.com.gesseff.books_api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

