package com.rabbitmp.producer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.rabbitmp.producer.config.RabbitMQConfig.MAIN_QUEUE;


@Component
public class RMQListener {
    @RabbitListener(queues = MAIN_QUEUE)
    public void  listen(String message){
        System.out.println("listened from MQ " + message);
    }
}
