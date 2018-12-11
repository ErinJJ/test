package com.example.testkafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class KafkaRecevierForChange {

    public static final String TOPIC_NAME = "test_change_send";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @KafkaListener(topics = {TOPIC_NAME}, containerFactory = "storeStatusChangeConsumer")
    public void statusMessage(String record) {
        logger.info("=====收到消息：{}", record);
        try {
            // 记录消息
            Optional<?> kafkaMessage = Optional.ofNullable(record);
            if (kafkaMessage.isPresent()) {
                logger.info("查询消息内容：{}", record);

            }
        } catch (Exception e) {
            logger.error("=======================处理失败：{}", record);
            e.printStackTrace();
        }
    }


}

