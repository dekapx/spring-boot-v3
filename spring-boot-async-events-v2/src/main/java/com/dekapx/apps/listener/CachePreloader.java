package com.dekapx.apps.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class CachePreloader {
    @EventListener
    public void onContextRefreshed(ContextRefreshedEvent event) {
        log.info("Context ready: {}", event.getTimestamp());
        CompletableFuture.runAsync(() -> {
            try {
                log.info("Preloading cache data...");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
