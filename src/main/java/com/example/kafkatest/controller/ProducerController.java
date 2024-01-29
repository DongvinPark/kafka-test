package com.example.kafkatest.controller;

import com.example.kafkatest.service.ConsumerService;
import com.example.kafkatest.service.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final Producer producer;
    private final ConsumerService consumerService;

    @GetMapping("/test-pub")
    public void testPub(){
        producer.pub("my-kafka-message-01");
    }

    /*@GetMapping("/check")
    public void checkItems(){
        consumerService.displayItems();
    }*/

}
