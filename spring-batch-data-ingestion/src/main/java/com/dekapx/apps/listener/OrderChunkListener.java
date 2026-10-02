package com.dekapx.apps.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
        log.debug("Starting chunk in step [{}]", context.getStepContext().getStepName());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.debug("Committed chunk in step [{}]", context.getStepContext().getStepName());
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        log.error("Chunk FAILED and was rolled back in step [{}]", context.getStepContext().getStepName());
    }
}
