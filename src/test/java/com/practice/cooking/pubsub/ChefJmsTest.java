package com.practice.cooking.pubsub;

import java.util.ArrayList;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import static org.assertj.core.api.Assertions.assertThat;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.publisher.Publisher;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChefJmsTest extends AbstractActiveMqJmsTest {

    private static final Queue  CHEF_QUEUE    = new ActiveMQQueue("chef_queue");
    private static final String MESSAGE_ADDED = "{\"id\":1,\"name\":\"Don\",\"restaurants\":null}";
    private              String receivedMessage;

    @Autowired
    private Publisher publisher;

    ChefDto chefDto;

    @BeforeEach
    void setUp() {
        chefDto = new ChefDto();
        chefDto.setId(1L);
        chefDto.setName("Don");
    }

    @Test
    public void testSendMessageToMultipleSubscribersViaQueueWhenAddingChef() throws Exception {
        for (int i = 0; i < 100; i++) {
            publisher.sendToConsumerWhenAddingNewRecord(CHEF_QUEUE, chefDto);
        }

        // Create the consumers
        List<MessageConsumer> consumers = messageQueueConsumers(CHEF_QUEUE, 100);

        for (MessageConsumer consumer : consumers) {
            Message text = consumer.receive(100);
            if (text != null) {
                receivedMessage = ((TextMessage) text).getText();
            }
            assertThat(receivedMessage).isNotEmpty();
            assertThat(receivedMessage).isEqualTo(MESSAGE_ADDED);
        }
    }

    @Test
    public void testSendMessageToMultipleSubscribersWhenDeletingChef() throws Exception {
        for (int i = 0; i < 100; i++) {
            publisher.sendToConsumerWhenDeletingRecord(CHEF_QUEUE, chefDto);
        }

        // Create the consumers
        List<MessageConsumer> consumers = messageQueueConsumers(CHEF_QUEUE, 100);

        for (MessageConsumer consumer : consumers) {
            Message text = consumer.receive(100);
            if (text != null) {
                receivedMessage = ((TextMessage) text).getText();
            }
            assertThat(receivedMessage).isNotEmpty();
            assertThat(receivedMessage).isEqualTo(MESSAGE_ADDED);
        }
    }

    private List<MessageConsumer> messageQueueConsumers(Queue queue, Integer number) throws JMSException {
        List<MessageConsumer> consumers = new ArrayList<>();
        for (int i = 0; i<number; i++) {
            consumers.add(session.createConsumer(queue));
        }
        return consumers;
    }

}


