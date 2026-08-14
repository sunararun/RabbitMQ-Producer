package com.rabbitmp.producer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MessageProcessorTest {
    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private MessageProcessor myService;
    @Test
    void processMessage() {
        String message = "Hello World";

        // Act
        myService.processMessage();
        System.out.println("hello");

        // Assert - verify that RabbitTemplate was called correctly
        verify(rabbitTemplate, times(1))
                .convertAndSend("main.exchange", "main.routing.key", message);
    }
}