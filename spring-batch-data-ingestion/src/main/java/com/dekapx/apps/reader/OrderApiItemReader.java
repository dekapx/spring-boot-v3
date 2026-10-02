package com.dekapx.apps.reader;

import com.dekapx.apps.client.OrdersApiClient;
import com.dekapx.apps.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Paginated ItemReader that pulls order records from a REST endpoint page by
 * page.
 * <p>
 * Implements {@link ItemStream} so the current page number is persisted in
 * the Spring Batch {@link ExecutionContext}, allowing a failed/restarted job
 * to resume from roughly where it left off instead of re-reading from page
 * zero.
 * <p>
 * Registered as a {@code @StepScope} bean from {@code BatchConfig} (not
 * annotated {@code @Component} here) so a fresh instance -- and fresh
 * mutable paging state -- is created per step execution/restart. The actual
 * HTTP call (with {@code @Retryable}) lives in the separate
 * {@link OrdersApiClient} bean; see that class's Javadoc for why.
 */
@Slf4j
public class OrderApiItemReader implements ItemReader<OrderDto>, ItemStream {

    private static final String PAGE_KEY = "orders.reader.page";

    private final OrdersApiClient ordersApiClient;

    @Value("${app.rest.page-size:200}")
    private int pageSize;

    private int currentPage = 0;
    private Iterator<OrderDto> currentPageIterator = Collections.emptyIterator();
    private boolean noMoreData = false;

    public OrderApiItemReader(OrdersApiClient ordersApiClient) {
        this.ordersApiClient = ordersApiClient;
    }

    @Override
    public OrderDto read() {
        if (!currentPageIterator.hasNext() && !noMoreData) {
            List<OrderDto> page = ordersApiClient.fetchPage(currentPage, pageSize);
            if (page == null || page.isEmpty()) {
                noMoreData = true;
                currentPageIterator = Collections.emptyIterator();
            } else {
                currentPageIterator = page.iterator();
                currentPage++;
            }
        }
        return currentPageIterator.hasNext() ? currentPageIterator.next() : null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey(PAGE_KEY)) {
            this.currentPage = executionContext.getInt(PAGE_KEY);
            log.info("Resuming orders reader from page {}", currentPage);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.putInt(PAGE_KEY, currentPage);
    }

    @Override
    public void close() throws ItemStreamException {
        // no resources to release
    }
}
