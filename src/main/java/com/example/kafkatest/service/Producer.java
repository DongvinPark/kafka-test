package com.example.kafkatest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {

    String topic = "test-topic3";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void pub(String msg) throws InterruptedException {
        for(int i=1; i<=10; i++){
            Thread.sleep(10);
            kafkaTemplate.send(topic, msg + i);
        }
    }

}
