package com.dekapx.apps.reader;

import com.dekapx.apps.model.EmployeeRedisDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Custom Spring Batch ItemReader that scans Redis using the SCAN command
 * and deserializes each value from JSON into an EmployeeRedisDto.
 *
 * Uses SCAN (not KEYS) to avoid blocking Redis on large datasets.
 * Thread-safe: each step execution gets its own reader instance (step-scoped).
 */
@Slf4j
public class RedisEmployeeItemReader implements ItemReader<EmployeeRedisDto> {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final String keyPattern;
    private final int scanCount;

    // Internal state — populated lazily on first read()
    private Iterator<String> keyIterator;
    private boolean initialized = false;

    public RedisEmployeeItemReader(RedisTemplate<String, String> redisTemplate,
                                   ObjectMapper objectMapper,
                                   String keyPattern,
                                   int scanCount) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.keyPattern = keyPattern;
        this.scanCount = scanCount;
    }

    /**
     * Called repeatedly by Spring Batch until null is returned (signals end of data).
     */
    @Override
    public EmployeeRedisDto read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (!initialized) {
            init();
        }

        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            String json = redisTemplate.opsForValue().get(key);

            if (json == null || json.isBlank()) {
                log.warn("Skipping key '{}' — value is null or blank", key);
                continue;
            }

            try {
                EmployeeRedisDto dto = objectMapper.readValue(json, EmployeeRedisDto.class);
                // Ensure the Redis key ID is propagated even if not in the JSON body
                if (dto.getId() == null || dto.getId().isBlank()) {
                    dto.setId(extractIdFromKey(key));
                }
                log.debug("Read employee [{}] from Redis key '{}'", dto.getId(), key);
                return dto;
            } catch (IOException e) {
                log.error("Failed to deserialize JSON for key '{}': {}", key, e.getMessage());
                throw new ParseException("Invalid JSON in Redis for key: " + key, e);
            }
        }

        log.info("RedisEmployeeItemReader exhausted all keys matching pattern '{}'", keyPattern);
        return null; // signals end of data to Spring Batch
    }

    /**
     * Eagerly loads all matching keys into memory using SCAN.
     * For very large datasets, consider a cursor-based iterator approach instead.
     */
    private void init() {
        log.info("Scanning Redis for keys matching pattern '{}' (count hint: {})", keyPattern, scanCount);

        List<String> keys = new ArrayList<>();
        ScanOptions options = ScanOptions.scanOptions()
                .match(keyPattern)
                .count(scanCount)
                .build();

        try (Cursor<String> cursor = redisTemplate.scan(options)) {
            while (cursor.hasNext()) {
                keys.add(cursor.next());
            }
        }

        log.info("Found {} Redis keys matching '{}'", keys.size(), keyPattern);
        keyIterator = keys.iterator();
        initialized = true;
    }

    /**
     * Extracts the ID portion from a Redis key like "employee:12345" → "12345"
     */
    private String extractIdFromKey(String key) {
        int colonIndex = key.lastIndexOf(':');
        return colonIndex >= 0 ? key.substring(colonIndex + 1) : key;
    }
}
