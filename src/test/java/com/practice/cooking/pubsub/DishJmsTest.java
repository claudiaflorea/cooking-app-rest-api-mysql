package com.practice.cooking.pubsub;

import java.util.ArrayList;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import static org.assertj.core.api.Assertions.assertThat;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.publisher.Publisher;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DishJmsTest extends AbstractActiveMqJmsTest {

    private static final Queue  DISH_QUEUE    = new ActiveMQQueue("dish_queue");
    private static final String MESSAGE_ADDED = "{\"id\":1,\"name\":\"Puree\",\"recipe\":null}";
    public static final  long   DISH_ID       = 1L;
    public static final  String DISH_NAME     = "Puree";
    private              String receivedMessage;

    @Autowired
    private Publisher publisher;

    DishDto dishDto = new DishDto();

    @Test
    public void testSendMessageToMultipleSubscribersViaQueueWhenAddingDish() throws Exception {

        dishDto.setId(DISH_ID);
        dishDto.setName(DISH_NAME);
        dishDto.setRecipe(null);

        for (int i = 0; i < 100; i++) {
            publisher.sendToConsumerWhenAddingNewRecord(DISH_QUEUE, dishDto);
        }

        // Create the consumers
        List<MessageConsumer> consumers = messageQueueConsumers(DISH_QUEUE, 100);

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
    public void testSendMessageToMultipleSubscribersWhenDeletingDish() throws Exception {

        dishDto.setId(DISH_ID);
        dishDto.setName(DISH_NAME);
        dishDto.setRecipe(null);

        for (int i = 0; i < 100; i++) {
            publisher.sendToConsumerWhenDeletingRecord(DISH_QUEUE, dishDto);
        }

        // Create the consumers
        List<MessageConsumer> consumers = messageQueueConsumers(DISH_QUEUE, 100);

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


