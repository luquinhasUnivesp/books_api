package br.com.gesseff.books_api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching // Habilita o cache na aplicação
@EnableScheduling // Habilita o agendamento de tarefas
public class LocalCacheConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalCacheConfig.class);

    @Bean
    public CacheManager bookCacheManager() {
        return new ConcurrentMapCacheManager("books");
    }

    @CacheEvict(value = "books", allEntries = true) // Limpa todas as entradas do cache "books"
    @Scheduled(fixedRateString = "${application.cache.schedule.rate:3600000}") // Limpa o cache a cada hora (3600000 ms)
    public void emptyLocalCache() {
        LOGGER.trace("[method: emptyLocalCache]");
    }
}

