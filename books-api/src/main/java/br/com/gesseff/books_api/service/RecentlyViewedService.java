package br.com.gesseff.books_api.service;

import br.com.gesseff.books_api.dto.BookDTO;
import br.com.gesseff.books_api.repository.BookRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class RecentlyViewedService {

    private static final String RECENTLY_VIEWED_KEY = "recentlyViewedBooks";
    private static final long MAX_RECENTLY_VIEWED = 10;

    private final StringRedisTemplate redisTemplate;
    private final BookRepository bookRepository;

    public RecentlyViewedService(StringRedisTemplate redisTemplate, BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.redisTemplate = redisTemplate;
    }

    public void addRecentlyViewedBook(Long bookId) {
        try {
            double score = System.currentTimeMillis();
            redisTemplate.opsForZSet().add(RECENTLY_VIEWED_KEY, bookId.toString(), score);

            Long size = redisTemplate.opsForZSet().size(RECENTLY_VIEWED_KEY);
            if (size != null && size > MAX_RECENTLY_VIEWED) {
                redisTemplate.opsForZSet().removeRange(RECENTLY_VIEWED_KEY, 0, size - MAX_RECENTLY_VIEWED - 1);
            }
        } catch (Exception e) {
            // Log the error and continue without failing the request
            System.err.println("Error adding recently viewed book: " + e.getMessage());
        }
    }

    public List<BookDTO> getRecentlyViewedBooks() {
        try {
            Set<String> ids = redisTemplate.opsForZSet().reverseRange(RECENTLY_VIEWED_KEY, 0, MAX_RECENTLY_VIEWED - 1);

            if (ids == null || ids.isEmpty()) {
                return Collections.emptyList();
            }
            List<Long> bookIds = ids.stream().map(Long::valueOf).toList();
            return bookRepository.findAllById(bookIds).stream().map(BookDTO::new).toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
