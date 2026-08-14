package com.rabbitmp.producer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TestConsumer123 {
    private final List<String> receivedMessages = new CopyOnWriteArrayList<>();

    @RabbitListener(queuesToDeclare = @Queue("test-queue"))
    void listen(String data) {
        receivedMessages.add(data);
    }

    public List<String> getReceivedMessages() {
        return receivedMessages;
    }
}
