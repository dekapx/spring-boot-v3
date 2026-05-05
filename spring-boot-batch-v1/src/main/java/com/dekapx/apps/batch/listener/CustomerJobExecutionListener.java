package com.dekapx.apps.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerJobExecutionListener extends JobExecutionListenerSupport {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("--------------------------------- BATCH JOB STARTED ---------------------------------");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("--------------------------------- BATCH JOB COMPLETED ---------------------------------");
    }
}
