package com.example.testkafka.producer;

import com.lianjia.home.crm.service.eventListeners.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * create by erin
 * 2018/11/7
 * 生产者发送类，通过调用kafkaTemplate.send()方法来发送消息
 */
@Component
public class KafkaProducerForChange {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private KafkaTemplate kafkaTemplate;


    public void caMessageProducer(String topicName, Object messageBody) {
        logger.info("此次发送消息的Topic:{},messageBody:{}", topicName, messageBody);
        String message = JsonUtil.toJson(messageBody);
        try {
            kafkaTemplate.send(topicName, message);
            logger.info("kafka消息发送成功！");
        } catch (Exception e) {
            logger.error("kafka消息发送失败：", e);
        }

    }
}
