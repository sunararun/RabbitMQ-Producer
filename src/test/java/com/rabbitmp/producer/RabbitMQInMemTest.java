package com.rabbitmp.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMQInMemTest {

    @Test
    public void testSendMessageAndConsume() throws IOException {
        // Arrange: Create the in-memory mock connection factory
        ConnectionFactory factory = new MockConnectionFactory();
        
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            
            String queueName = "my-test-queue";
            
            // Declare queue
            channel.queueDeclare(queueName, false, false, false, null);
            
            String message = "Hello RabbitMQ";
            
            // Act: Publish message
            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
            
            // Assert: Consume message back from the queue
            var response = channel.basicGet(queueName, true);
            
            Assertions.assertNotNull(response);
            String receivedMessage = new String(response.getBody(), StandardCharsets.UTF_8);
            System.out.println("EEEEEEEEEEEEEEE " + receivedMessage);
            Assertions.assertEquals("Hello RabbitMQ", receivedMessage);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
