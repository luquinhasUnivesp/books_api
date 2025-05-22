package br.com.gesseff.books_api.model;

import br.com.gesseff.books_api.dto.BookDTO;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity // indica que a classe é uma entidade
@EqualsAndHashCode(of = "id") // gera os metodos de hashcode e equals apenas para o id, o hashcode é um valor unico para cada objeto e o equals compara se o objeto é igual ao outro
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // gera o id automaticamente
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
