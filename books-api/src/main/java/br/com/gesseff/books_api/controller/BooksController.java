package br.com.gesseff.books_api.controller;

import br.com.gesseff.books_api.dto.BookDTO;
import br.com.gesseff.books_api.model.Book;
import br.com.gesseff.books_api.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; //classe que representa uma resposta http
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController //anotação que indica que a classe é um controller
@RequestMapping("/books") //anotação que indica o caminho base para as requisições, qual a url que esse controller vai responder
public class BooksController { // o controller é a classe onde a gente mapeia as requisições http que chegarão na nossa aplicação

    @Autowired
    private BookService service;

    @PostMapping //anotação que indica que esse método vai responder a requisições do tipo POST
    // transactional é uma anotação que indica que o método é transacional, ou seja, que ele vai ser executado dentro de uma transação
    @Transactional                       //requestBody Sem essa anotação o Spring não vai ler o corpo da requisição e mapear os campos dele para o DTO recebido como parâmetro.
    public ResponseEntity<Book> addBook(@RequestBody @Valid BookDTO books, UriComponentsBuilder uriBuilder) {
        var book = service.addBook(books);
        var uri = uriBuilder.path("/books/{id}").buildAndExpand(book.getId()).toUri(); //uriBuilder é uma classe que ajuda a construir a uri de resposta
        return ResponseEntity.created(uri).body(book);  //ResponseEntity é uma classe que representa uma resposta http, nesse caso, a resposta é um 201 Created
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
    public List<BookDTO> getBooksByGenre(@PathVariable String genre) { // PathVariable é uma anotação que indica que o valor da variável é um parâmetro da url
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
