package com.dekapx.apps.client;

import com.dekapx.apps.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Thin client wrapping the paged REST call to the orders source API, kept as
 * its OWN Spring bean (rather than a private/protected method on the item
 * reader) so that Spring AOP can actually apply the {@code @Retryable}
 * advice.
 * <p>
 * Important: {@code @Retryable} is implemented via a dynamic proxy. A call
 * from one method to another method on the SAME bean ("self-invocation")
 * bypasses the proxy entirely and silently disables the retry. Extracting
 * this into a separate bean, called externally from {@code OrderApiItemReader},
 * avoids that pitfall.
 */
@Slf4j
@Component
public class OrdersApiClient {

    private final WebClient webClient;

    @Value("${app.rest.orders-path:/api/orders}")
    private String ordersPath;

    public OrdersApiClient(WebClient ordersWebClient) {
        this.webClient = ordersWebClient;
    }

    /**
     * Retries transient failures (5xx responses, connection resets, timeouts)
     * up to 3 attempts with exponential backoff (1s, 2s, 4s). 4xx client
     * errors are NOT retried since retrying an identical bad request will
     * never succeed -- those propagate immediately.
     */
    @Retryable(
            retryFor = {WebClientResponseException.class, IOException.class, TimeoutException.class},
            noRetryFor = {WebClientResponseException.BadRequest.class, WebClientResponseException.NotFound.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2.0, maxDelay = 8000)
    )
    public List<OrderDto> fetchPage(int page, int pageSize) {
        log.info("Fetching page {} (size={}) from {}", page, pageSize, ordersPath);
        List<OrderDto> result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(ordersPath)
                        .queryParam("page", page)
                        .queryParam("size", pageSize)
                        .build())
                .retrieve()
                .bodyToFlux(OrderDto.class)
                .collectList()
                .block();
        log.info("Fetched {} order(s) on page {}", result == null ? 0 : result.size(), page);
        return result;
    }

    /**
     * Invoked once all retry attempts for {@link #fetchPage} are exhausted.
     * Logs and rethrows so the step's own retry/skip policy decides whether
     * to abort the job or skip forward, rather than silently returning an
     * empty page (which would look indistinguishable from "end of data").
     */
    @Recover
    public List<OrderDto> recover(Exception e, int page, int pageSize) {
        log.error("Exhausted retries fetching page {} (size={}): {}", page, pageSize, e.getMessage());
        throw new IllegalStateException("Failed to fetch orders page " + page + " after retries", e);
    }
}
