package com.practice.cooking.pubsub;

import java.util.ArrayList;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import static org.assertj.core.api.Assertions.assertThat;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.publisher.Publisher;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecipeJmsTest extends AbstractActiveMqJmsTest {

    private static final Queue  RECIPE_QUEUE  = new ActiveMQQueue("recipe_queue");
    private static final String MESSAGE_ADDED = "{\"id\":1,\"name\":\"Puree\",\"difficulty\":\"EASY\",\"ingredients\":null,\"cookingTime\":1,\"recipeType\":\"SIDE\"}";
    private              String receivedMessage;

    @Autowired
    private Publisher publisher;

    RecipeDto recipeDto = new RecipeDto(1L, "Puree", Difficulty.EASY, null, 1, RecipeType.SIDE);

    @Test
    public void testSendMessageToMultipleSubscribersViaQueueWhenAddingRecipe() throws Exception {
        for (int i = 0; i < 10; i++) {
            publisher.sendToConsumerWhenAddingNewRecord(RECIPE_QUEUE, recipeDto);
        }

        // Create the consumers
        List<MessageConsumer> consumers = messageQueueConsumers(RECIPE_QUEUE, 10);

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
    public void testSendMessageToMultipleSubscribersWhenDeletingRecipe() throws Exception {
        for (int i = 0; i < 10; i++) {
            publisher.sendToConsumerWhenDeletingRecord(RECIPE_QUEUE, recipeDto);
        }

        // Create the consumers
        List<MessageConsumer> consumers = messageQueueConsumers(RECIPE_QUEUE, 10);

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


