package com.example.testkafka.producer;

import com.google.common.collect.Maps;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

/**
 * create by erin
 * 2018/11/7
 * 生产者配置类，配置生产消息需要的参数
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

    //支持从配置文件中读取，使用value注解来获取配置文件中值
//    @Value("${kafka.producer.business.servers}")
//    private String servers;
//    @Value("${kafka.producer.business.retries}")
//    private Integer retries;
//    @Value("${kafka.producer.business.batch.size}")
//    private Integer batchSize;
//    @Value("${kafka.producer.business.linger}")
//    private Integer linger;
//    private Long bufferMemory = 40960L;

//    private String servers="kafka01-test.lianjia.com:9092,kafka02-test.lianjia.com:9092,kafka03-test.lianjia.com:9092";//kafka服务器地址
    private String servers="10.33.142.198:9092";
    private Integer retries=3;//重试次数
    private Integer batchSize=4096;
    private Integer linger=1;
    private Long bufferMemory = 40960L;


    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate(producerFactory());
    }

    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerForChangeConfig());
    }

    public Map<String, Object> producerForChangeConfig() {
        Map<String, Object> producer = Maps.newHashMap();
        producer.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        producer.put(ProducerConfig.RECEIVE_BUFFER_CONFIG, retries);
        producer.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        producer.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        producer.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        producer.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producer.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return producer;
    }

}

