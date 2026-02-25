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
            // usa o timestamp como score para garantir a ordem correta dos livros visualizados
            double score = System.currentTimeMillis();
            // Adiciona o livro ao ZSet, atualizando o score se o livro já existir
            redisTemplate.opsForZSet().add(RECENTLY_VIEWED_KEY, bookId.toString(), score);

            // Limita o tamanho do ZSet para os últimos 10 livros visualizados, o Sorted Set é uma estrutura de dados do Redis que armazena elementos únicos associados a um score, permitindo ordenação e operações eficientes.
            // O metodo removeRange é usado para remover os elementos mais antigos, garantindo que apenas os 10 livros mais recentes sejam mantidos no ZSet.
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
            // Recupera os IDs dos livros visualizados mais recentemente, ordenados do mais recente para o mais antigo
            Set<String> ids = redisTemplate.opsForZSet().reverseRange(RECENTLY_VIEWED_KEY, 0, MAX_RECENTLY_VIEWED - 1);

            if (ids == null || ids.isEmpty()) {
                return Collections.emptyList(); // Retorna uma lista vazia se não houver livros visualizados recentemente
            }
            // Converte os IDs de String para Long e busca os livros correspondentes no banco de dados (é realizada a conversão, pois os IDs são armazenados como Strings no Redis, mas o repositório espera Longs para buscar os livros)
            List<Long> bookIds = ids.stream().map(Long::valueOf).toList();
            // o stream é usado para converter os livros encontrados em BookDTOs e retornar a lista resultante
            return bookRepository.findAllById(bookIds).stream().map(BookDTO::new).toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
