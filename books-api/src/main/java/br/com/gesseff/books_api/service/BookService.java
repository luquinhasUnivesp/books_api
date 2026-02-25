package br.com.gesseff.books_api.service;

import br.com.gesseff.books_api.dto.BookDTO;
import br.com.gesseff.books_api.model.Book;
import br.com.gesseff.books_api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {

    private BookRepository repository;
    private final RecentlyViewedService recentlyViewedService;

    public BookService(BookRepository repository,RecentlyViewedService recentlyViewedService) {
        this.repository = repository;
        this.recentlyViewedService = recentlyViewedService;
    }

    public Book addBook(BookDTO book) {
        Book newBook = new Book(book);
        return repository.save(newBook);
    }

    @Cacheable(value = "books", cacheManager = "bookCacheManager")
    public List<BookDTO> listBooks() {
        return repository.findAll().stream().map(BookDTO::new).toList();
    }

    public BookDTO getBookById(Long id) {
        Book book = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado com o ID: " + id));
        recentlyViewedService.addRecentlyViewedBook(id);
        return new BookDTO(book);
    }

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
