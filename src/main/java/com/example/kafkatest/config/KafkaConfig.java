package com.example.kafkatest.config;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory(){
        Map<String, Object> myConfig = new HashMap<>();
        myConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "3.34.190.175:9092,13.124.91.53:9092,15.164.224.28:9092");
        myConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        myConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        myConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(myConfig);
    }

    /*
    이것은 옛날 버전이다. apache kafka client 라이브러리를 직접 사용하는 경우다.
    최근 버전은 spring for apache kafka 라이브러리에서 apache kafka client를 이미 포함하고 있어서
    프로듀서와 컨슈머를 더욱 간편하게 구현할 수 있다.
    단, 이렇게 하기 위해서는 스프링 부트 버전과 spring for apache kafka 버전의 호환성이 서로 맞아야 한다.
    자세한 내용은 스프링 공식 문서의 https://spring.io/projects/spring-kafka 이 부분을 참고하라.
    */
    @Bean
    public KafkaConsumer<String, String> getKafkaConsumer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "3.34.190.175:9092,13.124.91.53:9092,15.164.224.28:9092");
        props.put("group.id", "test-consumer");
        props.put("enable.auto.commit", "true");
        props.put("auto.offset.reset", "latest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("subject01"));
        return consumer;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory(){
        Map<String, Object> myConfig = new HashMap<>();
        myConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "3.34.190.175:9092,13.124.91.53:9092,15.164.224.28:9092");
        myConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        myConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(myConfig);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Object> myFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        myFactory.setConsumerFactory(consumerFactory());
        return myFactory;
    }

}
