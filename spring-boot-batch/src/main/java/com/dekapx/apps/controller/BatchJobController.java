package com.dekapx.apps.controller;

import com.dekapx.apps.batch.launcher.BatchJobLauncher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dekapx.apps.common.ResourceUrls.BASE_URL;
import static com.dekapx.apps.common.ResourceUrls.BATCH_JOB_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
public class BatchJobController {
    @Autowired
    BatchJobLauncher batchJobLauncher;

    @GetMapping(BATCH_JOB_URL)
    public String invokeBatch() {
        log.info("Customer Batch job invoked...");
        batchJobLauncher.triggerJob();
        return "Customer Batch job invoked...";
    }
}
