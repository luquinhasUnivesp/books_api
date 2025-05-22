package br.com.gesseff.books_api.controller;

import br.com.gesseff.books_api.dto.BookDTO;
import br.com.gesseff.books_api.model.Book;
import br.com.gesseff.books_api.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BookService service;

    @PostMapping
    @Transactional
    public ResponseEntity<Book> addBook(@RequestBody @Valid BookDTO books, UriComponentsBuilder uriBuilder) {
        var book = service.addBook(books);
        var uri = uriBuilder.path("/books/{id}").buildAndExpand(book.getId()).toUri();
        return ResponseEntity.created(uri).body(book);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> listBooks() {
        return ResponseEntity.ok(service.listBooks());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    @GetMapping(value = "/genre/{genre}")
    public List<BookDTO> getBooksByGenre(@PathVariable String genre) {
        return service.getBooksByGenre(genre);
    }

    @GetMapping(value = "/author/{author}")
    public List<BookDTO> getBooksByAuthor(@PathVariable String author) {
        return service.getBooksByAuthor(author);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
