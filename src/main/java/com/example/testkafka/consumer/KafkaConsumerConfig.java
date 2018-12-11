package com.example.testkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * create by erin
 * 2018/11/7
 * 消费者配置类
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    /*
    变更用 kafka
     */
//    @Value("${kafka.consumer.change.servers}")
//    private String servers;
//    @Value("${kafka.consumer.enable.auto.commit}")
//    private boolean enableAutoCommit;
//    @Value("${kafka.consumer.session.timeout}")
//    private String sessionTimeout;
//    @Value("${kafka.consumer.auto.commit.interval}")
//    private String autoCommitInterval;
//    @Value("${kafka.consumer.change.group.id}")
//    private String groupId;
//    @Value("${kafka.consumer.auto.offset.reset}")
//    private String autoOffsetReset;
//    @Value("${kafka.consumer.concurrency}")
//    private int concurrency;

//    private String servers="kafka01-test.lianjia.com:9092,kafka02-test.lianjia.com:9092,kafka03-test.lianjia.com:9092";
    private String servers="10.33.142.198:9092";
    private boolean enableAutoCommit=true;
    private String sessionTimeout="6000";
    private String autoCommitInterval="100";
    private String groupId="g1";
    private String autoOffsetReset="latest";
    private int concurrency=1;



    private String statusServers="kafka01-test.lianjia.com:9092,kafka02-test.lianjia.com:9092,kafka03-test.lianjia.com:9092";
    private String statusGroupId="status-test";

    public Map<String, Object> storeStandardIdInfoChangeConsumerConfig(String servers, String groupId) {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        return propsMap;
    }

    @Bean(value = "storeStatusChangeConsumer")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> storeStatusChangeConsumer() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<String, String> storeStandardIdInfoFactory =
                new DefaultKafkaConsumerFactory<>(storeStandardIdInfoChangeConsumerConfig(statusServers, statusGroupId));
        factory.setConsumerFactory(storeStandardIdInfoFactory);
        factory.getContainerProperties().setPollTimeout(3000);
        logger.info("状态变更用 kafka 建立完毕...servers:{}, groupId:{}", statusServers, statusGroupId);
        return factory;
    }
}
