package com.example.kafkatest.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final KafkaConsumer<String, String> kafkaConsumer;

    public void displayItems(){
        ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
        for(ConsumerRecord<String, String> record : records){
            String consumedItem = record.value();
            System.out.println("읽어들인 아이템 " + consumedItem);
        }
        System.out.println("실행완료!!");
    }

}
