package com.example.kafkatest.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final KafkaConsumer<String, String> kafkaConsumer;

    public void displayItems(){
        ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
        for(ConsumerRecord<String, String> record : records){
            String consumedItem = record.value();
            System.out.println("!!! Consumed Item : " + consumedItem);
        }
        System.out.println("!!! Consume Completed !!!");
    }

    @KafkaListener(topics = "subject01", groupId = "spring-consumer-01")
    public void consumer(String message){
        System.out.println("!!! Kafka Listener Output : " + message);
    }

}
