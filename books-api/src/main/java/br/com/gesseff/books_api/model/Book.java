package br.com.gesseff.books_api.model;

import br.com.gesseff.books_api.dto.BookDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
