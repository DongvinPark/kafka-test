package com.example.kafkatest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {

    String topic = "퍼블리시 하고자 하는 토픽의 이름";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void pub(String msg){
        kafkaTemplate.send(topic, msg);
    }

}
