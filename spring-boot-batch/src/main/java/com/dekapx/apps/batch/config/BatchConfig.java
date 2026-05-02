package com.dekapx.apps.batch.config;

import com.dekapx.apps.batch.listener.CustomerJobExecutionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private static final int BATCH_SIZE = 2;
    private static final String JOB_NAME = "archiveBatchJob";
    private static final String STEP_NAME = "archiveStep";

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CustomerJobExecutionListener jobExecutionListener;

    @Autowired
    private ItemReader customerItemReader;

    @Autowired
    private ItemProcessor customerItemProcessor;

    @Autowired
    private ItemWriter customerItemWriter;

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public Job archiveBatchJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener)
                .flow(archiveStep())
                .end()
                .build();
    }

    @Bean
    public SimpleAsyncTaskExecutor asyncTaskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("AsyncTaskExecutor");
        taskExecutor.setConcurrencyLimit(2);
        return taskExecutor;
    }

    @Bean
    public Step archiveStep() {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<String, String>chunk(BATCH_SIZE, transactionManager())
                .reader(customerItemReader)
                .processor(customerItemProcessor)
                .writer(customerItemWriter)
                .taskExecutor(asyncTaskExecutor())
                .build();
    }

}
