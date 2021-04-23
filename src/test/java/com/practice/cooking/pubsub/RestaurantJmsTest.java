package com.practice.cooking.pubsub;

import java.util.ArrayList;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import static org.assertj.core.api.Assertions.assertThat;
import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.publisher.Publisher;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestaurantJmsTest extends AbstractActiveMqJmsTest {

    private static final Queue  RESTAURANT_QUEUE = new ActiveMQQueue("restaurant_queue");
    private static final String MESSAGE_ADDED    = "{\"id\":1,\"name\":\"Casa Grande\",\"stars\":5,\"dishes\":null,\"chefs\":null}";
    private              String receivedMessage;

    @Autowired
    private Publisher publisher;

    RestaurantDto restaurantDto = new RestaurantDto(1L, "Casa Grande", 5, null, null);

    @Test
    public void testSendMessageToMultipleSubscribersViaQueueWhenAddingRestaurant() throws Exception {
        for (int i = 0; i < 10; i++) {
            publisher.sendToConsumerWhenAddingNewRecord(RESTAURANT_QUEUE, restaurantDto);
        }

        // Create the consumers
        List<MessageConsumer> consumers = messageQueueConsumers(RESTAURANT_QUEUE, 10);

        for (MessageConsumer consumer : consumers) {
            Message text = consumer.receive(100L);
            if (text != null) {
                receivedMessage = ((TextMessage) text).getText();
            }
            assertThat(receivedMessage).isNotEmpty();
            assertThat(receivedMessage).isEqualTo(MESSAGE_ADDED);
        }
    }

    @Test
    public void testSendMessageToMultipleSubscribersWhenDeletingRestaurant() throws Exception {
        for (int i = 0; i < 10; i++) {
            publisher.sendToConsumerWhenDeletingRecord(RESTAURANT_QUEUE, restaurantDto);
        }

        // Create the consumers
        List<MessageConsumer> consumers = messageQueueConsumers(RESTAURANT_QUEUE, 10);

        for (MessageConsumer consumer : consumers) {
            Message text = consumer.receive(100L);
            if (text != null) {
                receivedMessage = ((TextMessage) text).getText();
            }
            assertThat(receivedMessage).isNotEmpty();
            assertThat(receivedMessage).isEqualTo(MESSAGE_ADDED);
        }
    }

    private List<MessageConsumer> messageQueueConsumers(Queue queue, Integer number) throws JMSException {
        List<MessageConsumer> consumers = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            consumers.add(session.createConsumer(queue));
        }
        return consumers;
    }

}


