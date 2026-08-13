package com.rabbitmp.producer;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RabbitTestConfig {
    @Bean
    public TestRabbitTemplate testRabbitTemplate(ConnectionFactory connectionFactory) {
        return new TestRabbitTemplate(connectionFactory);
    }
}
