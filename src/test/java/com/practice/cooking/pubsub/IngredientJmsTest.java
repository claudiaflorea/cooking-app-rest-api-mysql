package com.practice.cooking.pubsub;

import java.util.ArrayList;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import static org.assertj.core.api.Assertions.assertThat;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.model.Unit;
import com.practice.cooking.publisher.Publisher;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IngredientJmsTest extends AbstractActiveMqJmsTest {

    private static final Queue  INGREDIENT_QUEUE = new ActiveMQQueue("ingredient_queue");
    private static final String MESSAGE_ADDED    = "{\"id\":1,\"name\":\"Potato\",\"quantity\":1.0,\"unit\":\"KG\"}";
    private              String receivedMessage;

    @Autowired
    private Publisher publisher;

    IngredientDto ingredientDto = new IngredientDto(1L, "Potato", 1, Unit.KG);

    @Test
    public void testSendMessageToMultipleSubscribersViaQueueWhenAddingIngredient() throws Exception {
        for (int i = 0; i < 10; i++) {
            publisher.sendToConsumerWhenAddingNewRecord(INGREDIENT_QUEUE, ingredientDto);
        }

        // Create the consumers
        List<MessageConsumer> consumers = messageQueueConsumers(INGREDIENT_QUEUE, 10);

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
    public void testSendMessageToMultipleSubscribersWhenDeletingIngredient() throws Exception {
        for (int i = 0; i < 10; i++) {
            publisher.sendToConsumerWhenDeletingRecord(INGREDIENT_QUEUE, ingredientDto);
        }

        // Create the consumers
        List<MessageConsumer> consumers = messageQueueConsumers(INGREDIENT_QUEUE, 10);

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
        for (int i = 0; i<number; i++) {
            consumers.add(session.createConsumer(queue));
        }
        return consumers;
    }

}


