package com.example.cpp;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class CppApplication {

    @Bean
    public DefaultMQProducer producer(){

        DefaultMQProducer producer = new DefaultMQProducer("pg1");
        producer.setNamesrvAddr("localhost:9876");
        try {
            producer.start();
            log.info("Producer 启动成功");
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
        return producer;
    }

    public static void main(String[] args) {
        SpringApplication.run(CppApplication.class, args);
    }

}
