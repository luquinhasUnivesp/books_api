package br.com.gesseff.books_api.service;

import br.com.gesseff.books_api.dto.BookDTO;
import br.com.gesseff.books_api.model.Book;
import br.com.gesseff.books_api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// Classe que contém a lógica de negócio da aplicação
@Service // Indica que a classe é um serviço
public class BookService {

    @Autowired // Injeção de dependência
    private BookRepository repository;

    public Book addBook(BookDTO book) {
        Book newBook = new Book(book); // Convertendo o DTO para o modelo
        return repository.save(newBook);   // Salvando no banco de dados um objeto do tipo Book (entidade, pois é uma classe que representa uma tabela no banco de dados)
    }

    public List<BookDTO> listBooks() {
        return repository.findAll().stream().map(BookDTO::new).toList();
    } // convertendo a lista de entidades para uma lista de DTOs

    public BookDTO getBookById(Long id) {
        Book book = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado com o ID: " + id));
        return new BookDTO(book);
    } // não precisei criar um método findById no repositório, pois o Spring Data JPA já fornece um método que faz isso

    public List<BookDTO> getBooksByGenre(String genre) {
        return repository.findByGenre(genre).stream().map(BookDTO::new).toList();
    }

    public List<BookDTO> getBooksByAuthor(String author) {
        return repository.findByAuthor(author).stream().map(BookDTO::new).toList();
    }

    public void deleteBook(Long id) {
        repository.deleteById(id);
    }


}
