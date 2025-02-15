package br.com.gesseff.books_api.controller;

import br.com.gesseff.books_api.dto.BookDTO;
import br.com.gesseff.books_api.model.Book;
import br.com.gesseff.books_api.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BookRepository repository;

    @PostMapping
    @Transactional
    public void addBook(@RequestBody @Valid BookDTO books) {
        repository.save(new Book(books));
    }

    @GetMapping
    public List<BookDTO> listBooks() {
        return repository.findAll().stream().map(BookDTO::new).toList();
    }

    @GetMapping(value = "/{id}")
    public List<BookDTO> getBookById(@PathVariable Long id) {
        return  repository.findById(id).stream().map(BookDTO::new).toList();
    }

    @GetMapping(value = "/genre/{genre}")
    public List<BookDTO> getBooksByGenre(@PathVariable String genre) {
        return repository.findByGenre(genre).stream().map(BookDTO::new).toList();
    }

    @GetMapping(value = "/author/{author}")
    public List<BookDTO> getBooksByAuthor(@PathVariable String author){
        return repository.findByAuthor(author).stream().map(BookDTO::new).toList();
    }
}
