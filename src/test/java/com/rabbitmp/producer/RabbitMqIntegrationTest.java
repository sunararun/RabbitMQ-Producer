package com.rabbitmp.producer;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.RabbitMQContainer;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.waitAtMost;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConsumer123.class)
class RabbitMqIntegrationTest {

    @ServiceConnection
    static RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:4.0-alpine");

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private TestConsumer123 testConsumer;

    @Test
    void testMessageProcessing() {
        String message = "Hello Testcontainers!";
        this.amqpTemplate.convertAndSend("test-queue", message);
        List<String> stringList = testConsumer.getReceivedMessages();

        waitAtMost(Duration.ofSeconds(5)).untilAsserted(() -> {
            assertThat(testConsumer.getReceivedMessages());
        });
        System.out.println("^^^^^^^^^^^^^^=>> " + stringList);
    }
/*
    @TestConfiguration
    static class TestConfig {
        @Bean
        public TestConsumer testConsumer() {
            return new TestConsumer();
        }
    }

    static class TestConsumer {
        private final List<String> receivedMessages = new CopyOnWriteArrayList<>();

        @RabbitListener(queuesToDeclare = @Queue("test-queue"))
        void listen(String data) {
            receivedMessages.add(data);
        }

        public List<String> getReceivedMessages() {
            return receivedMessages;
        }
    }*/
}
