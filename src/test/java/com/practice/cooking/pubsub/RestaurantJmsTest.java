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

    public static final long RESTAURANT_ID = 1L;
    private static final Queue RESTAURANT_QUEUE = new ActiveMQQueue("restaurant_queue");
    private static final String MESSAGE_ADDED    = "{\"id\":1,\"name\":\"Casa Grande\",\"stars\":5,\"dishes\":null,\"chefs\":null}";
    public static final String RESTAURANT_NAME = "Casa Grande";
    public static final int RESTAURANT_STARS = 5;
    private              String receivedMessage;

    @Autowired
    private Publisher publisher;

    RestaurantDto restaurantDto = new RestaurantDto();

    @Test
    public void testSendMessageToMultipleSubscribersViaQueueWhenAddingRestaurant() throws Exception {
        
        restaurantDto.setId(RESTAURANT_ID);
        restaurantDto.setName(RESTAURANT_NAME);
        restaurantDto.setStars(RESTAURANT_STARS);
        restaurantDto.setChefs(null);
        restaurantDto.setDishes(null);
        
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

        restaurantDto.setId(RESTAURANT_ID);
        restaurantDto.setName(RESTAURANT_NAME);
        restaurantDto.setStars(RESTAURANT_STARS);
        restaurantDto.setChefs(null);
        restaurantDto.setDishes(null);
        
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


