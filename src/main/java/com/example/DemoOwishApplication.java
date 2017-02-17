package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@ComponentScan("com.example")
@EnableJpaRepositories("com.example.repository")
@EnableElasticsearchRepositories("com.example.repository.elastic")
@EnableWebSocketMessageBroker
public class DemoOwishApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoOwishApplication.class, args);
	}
}
