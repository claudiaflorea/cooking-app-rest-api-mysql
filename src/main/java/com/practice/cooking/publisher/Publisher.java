package com.practice.cooking.publisher;

import javax.jms.Queue;
import javax.jms.Topic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Publisher {
   
    private final JmsTemplate jmsTemplate;

    private final ObjectMapper mapper;

    public void sendToConsumerWhenAddingNewRecord(Queue queue, Object o) throws JsonProcessingException {
        String result = mapper.writeValueAsString(o);
        jmsTemplate.convertAndSend(queue, result);
        log.info("Producer > Message sent to subscriber. " + result + " was created.");
    }

    public void sendToConsumerWhenDeletingRecord(Queue queue, Object o) throws JsonProcessingException {
        String result = mapper.writeValueAsString(o);
        jmsTemplate.convertAndSend(queue, result);
        log.info("Producer > Message sent to subscriber. " + result + " was deleted.");
    }

    public void sendToConsumerWhenUpdatingRecord(Queue queue, Object o) throws JsonProcessingException {
        String result = mapper.writeValueAsString(o);
        jmsTemplate.convertAndSend(queue, result);
        log.info("Producer > Message sent to subscriber. " + result + " was updated.");
    }
    
    public void notifyAllSubscribersWhenAddingRecord(Topic topic, Object o) throws JsonProcessingException {
        String result = mapper.writeValueAsString(o);
        jmsTemplate.convertAndSend(topic, result);
        log.info("Producer > Message sent to all subscribers. " + result + " was created.");
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 1000)
    public void sayHello() {
        Topic topic = new ActiveMQTopic("hello_topic");
        jmsTemplate.convertAndSend(topic, "I just want to say hello!! ");
        log.info("Producer > Said hello to all subscribers");
    }
    
}
