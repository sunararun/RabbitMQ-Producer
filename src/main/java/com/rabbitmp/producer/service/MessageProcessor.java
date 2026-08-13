package com.rabbitmp.producer.service;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.rabbitmp.producer.config.RabbitMQConfig.*;

@Service
@AllArgsConstructor
public class MessageProcessor {
    private final RabbitTemplate rabbitTemplate;
    public void processMessage() {
        rabbitTemplate.convertAndSend(MAIN_EXCHANGE
                ,MAIN_ROUTING_KEY,"Hello World");
    }
}
