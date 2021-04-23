package com.practice.cooking.pubsub;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

@SpringBootTest
public class PublisherTest {

    private static final Queue  QUEUE   = new ActiveMQQueue("queue");
    private static final String MESSAGE = "This is a test!";

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void testQueue() {
        jmsTemplate.convertAndSend(QUEUE, MESSAGE);
        jmsTemplate.setReceiveTimeout(10_000);
        Assertions.assertThat(jmsTemplate.receiveAndConvert(QUEUE)).isEqualTo(MESSAGE);
    }

}
