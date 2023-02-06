package com.dekapx.apps.util;

import com.dekapx.apps.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class MessageGenerator {
    @Autowired
    private StreamBridge streamBridge;

    @Scheduled(cron = "*/2 * * * * *")
    public void generateAndSendMessages() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            this.streamBridge.send("producer-out-0",
                    new Message("Test Message from Stream bridge #" + i));
        });
    }
}
