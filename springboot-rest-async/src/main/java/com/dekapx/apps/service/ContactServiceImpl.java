package com.dekapx.apps.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService {
    @Override
    public void doSync() {
        IntStream.rangeClosed(1, 5)
                .forEach(i -> {
                    try {
                        log.info("Running [{}]", i);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
        log.info("finish");
    }

    @Async
    @Override
    public CompletableFuture<Void> doAsync() {
        log.info("start");
        return CompletableFuture.allOf()
                .thenAccept(future -> {
                    IntStream.rangeClosed(1, 5)
                                    .forEach(i -> {
                                        try {
                                            log.info("Running [{}]", i);
                                            TimeUnit.SECONDS.sleep(1);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                    log.info("finish");
                });
    }
}
