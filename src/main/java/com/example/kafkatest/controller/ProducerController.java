package com.example.kafkatest.controller;

import com.example.kafkatest.service.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final Producer producer;

    @GetMapping("/test-pub")
    public void testPub() throws InterruptedException {
        Thread.sleep(100);
        producer.pub("my-kafka-message-from-8080-");
    }

}
