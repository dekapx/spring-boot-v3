package com.dekapx.apps.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class MessageGenerator {
    @Autowired
    private StreamBridge streamBridge;

    @Scheduled(cron = "*/1 * * * * *")
    public void generateAndSendMessages() {
        IntStream.rangeClosed(1, 500).forEach(i -> {
            this.streamBridge.send("producer-out-0","StreamBridge - Test Message #" + i);
        });
    }
}
