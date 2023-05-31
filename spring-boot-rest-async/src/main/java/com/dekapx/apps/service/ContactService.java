package com.dekapx.apps.service;

import java.util.concurrent.CompletableFuture;

public interface ContactService {
    void doSync();
    CompletableFuture<Void> doAsync();
}
