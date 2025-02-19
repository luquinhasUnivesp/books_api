package br.com.gesseff.books_api.service;

import br.com.gesseff.books_api.dto.BookDTO;
import br.com.gesseff.books_api.model.Book;
import br.com.gesseff.books_api.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBookTest() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Title");
        bookDTO.setAuthor("Author");
        bookDTO.setGenre("Genre");
        bookDTO.setDescription("Description");

        var saveBook = new Book(bookDTO);
        saveBook.setId(1L);

        when(repository.save(any(Book.class))).thenReturn(saveBook);

        var result = service.addBook(bookDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Title", result.getTitle());

        verify(repository, times(1)).save(any(Book.class));
    }


    @Test
    void listBooksTest() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setGenre("Genre");
        book.setDescription("Description");

        when(repository.findAll()).thenReturn(java.util.List.of(book));

        var result = service.listBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Title", result.get(0).getTitle());

        verify(repository, times(1)).findAll();
    }

    @Test
    void getBookByIdTest() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setGenre("Genre");
        book.setDescription("Description");

        when(repository.findById(1L)).thenReturn(java.util.Optional.of(book));

        var result = service.getBookById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Title", result.getTitle());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getBooksByGenreTest(){
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setGenre("Genre");
        book.setDescription("Description");

        when(repository.findByGenre("Genre")).thenReturn(java.util.List.of(book));

        var result = service.getBooksByGenre("Genre");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Title", result.get(0).getTitle());

        verify(repository, times(1)).findByGenre("Genre");
    }

}