package com.rabbitmp.producer;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(RabbitTestConfig.class)
class RabbitMockTest {

    @Autowired
    private TestRabbitTemplate testRabbitTemplate;

    @Test
    void testMessageRouting() {
        // Sends directly to the @RabbitListener listening on "myQueue" without a broker
        String response = (String) testRabbitTemplate.convertSendAndReceive("myQueue","mykey", "Hello World");
        assertEquals("Expected Reply", response);
    }
}
