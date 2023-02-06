package com.dekapx.apps.producer;

import com.dekapx.apps.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
public class KafkaMessageProducer {
    @Bean
    public Supplier<Message> producer() {
        return () -> new Message(" Test message from Message Producer");
    }
}
