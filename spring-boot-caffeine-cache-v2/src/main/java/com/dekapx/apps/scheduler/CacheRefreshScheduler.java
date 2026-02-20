package com.dekapx.apps.scheduler;

import com.dekapx.apps.cache.ContactCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class CacheRefreshScheduler {
    private ContactCacheManager contactCacheManager;

    @Autowired
    public CacheRefreshScheduler(final ContactCacheManager contactCacheManager) {
        this.contactCacheManager = contactCacheManager;
    }

    @Scheduled(fixedRate = 60000)
    public void refreshCache() {
        log.info("Refreshing cache...");
        this.contactCacheManager.invalidateAndReload();
    }
}
