package com.practice.cooking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VirtualTopicProducerController {

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper mapper;

    @GetMapping(value = "/sendMessageVirtual")
    public ResponseEntity<String> sendMessageVirtual() throws JsonProcessingException {
        ActiveMQTopic topic = new ActiveMQTopic("virtual_topic");
        String message = "New message!!!!";
        jmsTemplate.convertAndSend(topic, mapper.writeValueAsString(message));
        log.info("Producer > Message sent to subscriber via virtual topic ");
        return new ResponseEntity<>("Message sent: " + message, HttpStatus.OK);
    }
}
