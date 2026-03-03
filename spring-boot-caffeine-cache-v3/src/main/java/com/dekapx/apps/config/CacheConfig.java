package com.dekapx.apps.config;

import com.dekapx.apps.entity.Contact;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public Cache<String, Map<Long, Contact>> caffeineCache() {
        return Caffeine.newBuilder()
                .initialCapacity(2)
                .maximumSize(5)
                .evictionListener(removalListener())
                .recordStats()
                .build();
    }

    private RemovalListener removalListener() {
        return (key, value, cause) -> log.info("Key {} was removed ({})", key, cause);
    }
}
