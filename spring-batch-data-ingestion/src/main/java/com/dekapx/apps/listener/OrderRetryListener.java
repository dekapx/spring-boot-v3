package com.dekapx.apps.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

/**
 * Logs each retry attempt made by the fault-tolerant step (e.g. when the
 * writer hits a transient DataAccessException). Distinct from the
 * {@code @Retryable} annotation used in {@code OrdersApiClient}, which
 * retries the REST call itself -- this listener observes retries
 * orchestrated by Spring Batch's own chunk-level RetryTemplate.
 */
@Slf4j
@Component
public class OrderRetryListener implements RetryListener {

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.warn("Retry attempt #{} failed: {}", context.getRetryCount(), throwable.toString());
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        if (context.getRetryCount() > 0) {
            log.info("Retry sequence finished after {} attempt(s), success={}",
                    context.getRetryCount(), throwable == null);
        }
    }
}
