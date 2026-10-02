package com.dekapx.apps.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Logs job-level lifecycle events: start time, end time, duration, and a
 * summary of read/write/skip counts aggregated across all steps. Good
 * extension point for pushing metrics (Micrometer/Prometheus) or alerts
 * (e.g. Slack on failure) in a real deployment.
 */
@Slf4j
@Component
public class JobCompletionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("=== Job [{}] STARTING - id={}, parameters={} ===",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getJobId(),
                jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Duration duration = Duration.between(jobExecution.getStartTime(), jobExecution.getEndTime());

        long readCount = 0, writeCount = 0, skipCount = 0;
        for (StepExecution step : jobExecution.getStepExecutions()) {
            readCount += step.getReadCount();
            writeCount += step.getWriteCount();
            skipCount += step.getSkipCount();
        }

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("=== Job [{}] COMPLETED in {} ms - read={}, written={}, skipped={} ===",
                    jobExecution.getJobInstance().getJobName(), duration.toMillis(),
                    readCount, writeCount, skipCount);
        } else {
            log.error("=== Job [{}] FINISHED WITH STATUS {} in {} ms - read={}, written={}, skipped={} - exceptions={} ===",
                    jobExecution.getJobInstance().getJobName(), jobExecution.getStatus(), duration.toMillis(),
                    readCount, writeCount, skipCount, jobExecution.getAllFailureExceptions());
        }
    }
}
