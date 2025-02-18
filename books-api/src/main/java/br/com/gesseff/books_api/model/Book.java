package br.com.gesseff.books_api.model;

import br.com.gesseff.books_api.dto.BookDTO;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String genre;
    private String description;

    public Book() {
    }

    public Book(BookDTO books) {
        this.title = books.getTitle();
        this.author = books.getAuthor();
        this.genre = books.getGenre();
        this.description = books.getDescription();
    }
}
