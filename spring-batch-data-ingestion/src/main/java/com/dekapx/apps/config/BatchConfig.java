package com.dekapx.apps.config;

import com.dekapx.apps.client.OrdersApiClient;
import com.dekapx.apps.dto.OrderDto;
import com.dekapx.apps.entity.Order;
import com.dekapx.apps.exception.OrderProcessingException;
import com.dekapx.apps.listener.JobCompletionListener;
import com.dekapx.apps.listener.OrderChunkListener;
import com.dekapx.apps.listener.OrderRetryListener;
import com.dekapx.apps.listener.OrderSkipListener;
import com.dekapx.apps.listener.OrderStepExecutionListener;
import com.dekapx.apps.processor.OrderItemProcessor;
import com.dekapx.apps.reader.OrderApiItemReader;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.concurrent.TimeoutException;

@Configuration
public class BatchConfig {

    @Value("${app.batch.chunk-size:200}")
    private int chunkSize;

    @Value("${app.batch.skip-limit:100}")
    private int skipLimit;

    @Value("${app.batch.write-retry-limit:3}")
    private int writeRetryLimit;

    @Value("${app.batch.concurrency:4}")
    private int concurrency;

    /**
     * Step-scoped so a fresh reader (and its internal paging state) is
     * created for every step execution / restart.
     */
    @Bean
    @StepScope
    public OrderApiItemReader orderApiItemReader(OrdersApiClient ordersApiClient) {
        return new OrderApiItemReader(ordersApiClient);
    }

    /**
     * {@code usePersist(false)} makes the writer use {@code EntityManager.merge()}
     * instead of {@code persist()}. This is what makes the ingestion upsert:
     * entities with a null id (brand-new orders, see {@code OrderItemProcessor})
     * are inserted; entities carrying the id of an existing row (orders whose
     * orderNumber already existed) are updated. {@code persist()} alone would
     * only ever insert and would fail/duplicate on re-runs.
     */
    @Bean
    public JpaItemWriter<Order> orderItemWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<Order>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(false)
                .build();
    }

    @Bean
    public TaskExecutor batchTaskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("batch-worker-");
        executor.setConcurrencyLimit(concurrency);
        return executor;
    }

    @Bean
    public Step importOrdersStep(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  OrderApiItemReader orderApiItemReader,
                                  OrderItemProcessor orderItemProcessor,
                                  JpaItemWriter<Order> orderItemWriter,
                                  OrderStepExecutionListener stepExecutionListener,
                                  OrderSkipListener skipListener,
                                  OrderChunkListener chunkListener,
                                  OrderRetryListener retryListener,
                                  TaskExecutor batchTaskExecutor) {

        return new StepBuilder("importOrdersStep", jobRepository)
                .<OrderDto, Order>chunk(chunkSize, transactionManager)
                .reader(orderApiItemReader)
                .processor(orderItemProcessor)
                .writer(orderItemWriter)
                .faultTolerant()
                // Skip bad/invalid records instead of failing the whole job
                .skipLimit(skipLimit)
                .skip(OrderProcessingException.class)
                .skip(DataIntegrityViolationException.class)
                .noSkip(NullPointerException.class)
                // Retry transient REST / DB errors at the chunk level
                .retryLimit(writeRetryLimit)
                .retry(WebClientResponseException.class)
                .retry(DataAccessException.class)
                .retry(TimeoutException.class)
                .listener(retryListener)
                .listener(stepExecutionListener)
                .listener(skipListener)
                .listener(chunkListener)
                .taskExecutor(batchTaskExecutor)
                .throttleLimit(concurrency)
                .build();
    }

    @Bean
    public Job importOrdersJob(JobRepository jobRepository,
                                Step importOrdersStep,
                                JobCompletionListener jobCompletionListener) {
        return new JobBuilder("importOrdersJob", jobRepository)
                .listener(jobCompletionListener)
                .start(importOrdersStep)
                .build();
    }
}
