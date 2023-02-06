package com.dekapx.apps;

import com.dekapx.apps.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Consumer<Message> consumer() {
		return message -> log.info("Message received {}", message);
	}

	@Bean
	public Supplier<Message> producer() {
		return () -> new Message(" Test message from Streams");
	}
}
