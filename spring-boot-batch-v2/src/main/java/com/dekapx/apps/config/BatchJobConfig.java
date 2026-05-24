package com.dekapx.apps.config;

import com.dekapx.apps.model.Employee;
import com.dekapx.apps.model.EmployeeRedisDto;
import com.dekapx.apps.processor.EmployeeItemProcessor;
import com.dekapx.apps.reader.RedisEmployeeItemReader;
import com.dekapx.apps.writer.EmployeePostgresItemWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EmployeeItemProcessor processor;
    private final EmployeePostgresItemWriter writer;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${batch.chunk-size:50}")
    private int chunkSize;

    @Value("${batch.skip-limit:10}")
    private int skipLimit;

    @Value("${batch.retry-limit:3}")
    private int retryLimit;

    @Value("${batch.redis.key-pattern:employee:*}")
    private String keyPattern;

    @Value("${batch.redis.scan-count:100}")
    private int scanCount;

    // -----------------------------------------------------------------------
    // Job
    // -----------------------------------------------------------------------

    @Bean
    public Job employeeEtlJob(Step employeeEtlStep) {
        return new JobBuilder("employeeEtlJob", jobRepository)
                .incrementer(new RunIdIncrementer())   // ensures unique JobInstance per run
                .listener(jobExecutionListener())
                .start(employeeEtlStep)
                .build();
    }

    // -----------------------------------------------------------------------
    // Step
    // -----------------------------------------------------------------------

    @Bean
    public Step employeeEtlStep() {
        return new StepBuilder("employeeEtlStep", jobRepository)
                .<EmployeeRedisDto, Employee>chunk(chunkSize, transactionManager)
                .reader(redisEmployeeItemReader())
                .processor(processor)
                .writer(writer)
                // Skip bad records up to skipLimit without failing the whole job
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(skipLimit)
                // Retry transient failures (e.g. Redis timeouts)
                .retry(org.springframework.dao.TransientDataAccessException.class)
                .retryLimit(retryLimit)
                .listener(stepExecutionListener())
                .build();
    }

    // -----------------------------------------------------------------------
    // Reader — NOT @StepScope here because RedisEmployeeItemReader manages its
    // own state; a simple prototype bean is sufficient.
    // -----------------------------------------------------------------------

    @Bean
    @StepScope
    public RedisEmployeeItemReader redisEmployeeItemReader() {
        return new RedisEmployeeItemReader(redisTemplate, objectMapper, keyPattern, scanCount);
    }

    // -----------------------------------------------------------------------
    // Listeners
    // -----------------------------------------------------------------------

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("=== Starting job: {} | RunId: {} ===",
                        jobExecution.getJobInstance().getJobName(),
                        jobExecution.getJobParameters().getLong("run.id"));
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                BatchStatus status = jobExecution.getStatus();
                long readCount  = jobExecution.getStepExecutions().stream()
                        .mapToLong(StepExecution::getReadCount).sum();
                long writeCount = jobExecution.getStepExecutions().stream()
                        .mapToLong(StepExecution::getWriteCount).sum();
                long skipCount  = jobExecution.getStepExecutions().stream()
                        .mapToLong(StepExecution::getSkipCount).sum();

                log.info("=== Job finished: {} | Status: {} | Read: {} | Written: {} | Skipped: {} ===",
                        jobExecution.getJobInstance().getJobName(),
                        status, readCount, writeCount, skipCount);

                if (status == BatchStatus.FAILED) {
                    jobExecution.getAllFailureExceptions()
                            .forEach(ex -> log.error("Job failure: {}", ex.getMessage()));
                }
            }
        };
    }

    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new StepExecutionListener() {
            @Override
            public void beforeStep(StepExecution stepExecution) {
                log.info("--- Step '{}' starting ---", stepExecution.getStepName());
            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                log.info("--- Step '{}' finished | Read: {} | Written: {} | Skipped: {} | Commits: {} ---",
                        stepExecution.getStepName(),
                        stepExecution.getReadCount(),
                        stepExecution.getWriteCount(),
                        stepExecution.getSkipCount(),
                        stepExecution.getCommitCount());
                return stepExecution.getExitStatus();
            }
        };
    }
}
