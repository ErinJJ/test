package com.example.testkafka.controller;

import com.example.testkafka.producer.KafkaProducerForChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/kafka")
public class KafkaTestController {
    @Autowired
    private KafkaProducerForChange kafkaProducerForChange;

    @RequestMapping(value = "/test",method =RequestMethod.GET )
    @ResponseBody
    public void testKafka(){
        Object obj="this is kafka test";
        kafkaProducerForChange.caMessageProducer("test_change_send",obj);
    }


}
