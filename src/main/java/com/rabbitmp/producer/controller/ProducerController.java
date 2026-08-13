package com.rabbitmp.producer.controller;

import com.rabbitmp.producer.service.MessageProcessor;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/api/mq")
public class ProducerController {
    private final MessageProcessor processor;

    @GetMapping("/save")
    public String sendMessage(){
        processor.processMessage();
        return "hello ";
    }
}
