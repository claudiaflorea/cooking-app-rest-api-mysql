package com.practice.cooking.pubsub;

import java.util.Arrays;
import javax.jms.Queue;
import javax.jms.Topic;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.mapper.ChefDtoToEntityMapper;
import com.practice.cooking.mapper.ChefEntityToDtoMapper;
import com.practice.cooking.model.Chef;
import com.practice.cooking.publisher.Publisher;
import com.practice.cooking.repository.ChefRepository;
import com.practice.cooking.service.ChefService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ChefPubSubTest {

    private static final String CHEF_COLLIN = "Chef. Collin";
    private static final String CHEF_CONNOR = "Chef. Connor";

    private static final long ID = 1L;
    
    @Mock
    private ChefRepository repository;
    
    @Mock
    private Publisher producer;
    
    @Mock
    private ChefDtoToEntityMapper dtoToEntityMapper;

    @Mock
    private ChefEntityToDtoMapper entityToDtoMapper;
    
    @InjectMocks
    private ChefService chefService;

    private static final Queue CHEF_QUEUE = new ActiveMQQueue("chef_queue");
    private static final Topic CHEF_TOPIC = new ActiveMQTopic("chef_topic");

    ChefDto chefDto;
    Chef chef;

    @BeforeEach
    void init() throws JsonProcessingException {
        //given
        chefDto = new ChefDto(ID, CHEF_COLLIN);
        chef = new Chef(ID, CHEF_COLLIN);
        when(repository.save(chef)).thenReturn(chef);
        when(repository.findById(ID)).thenReturn(java.util.Optional.ofNullable(chef));
        when(repository.findAllByName(CHEF_COLLIN)).thenReturn(Arrays.asList(chef));
        when(dtoToEntityMapper.dtoToEntity(chefDto)).thenReturn(chef);
        when(entityToDtoMapper.entityToDto(chef)).thenReturn(chefDto);

        //when
        chefService.add(chefDto);
    }
    
    @AfterEach
    void clean() throws JsonProcessingException {
        chefService.delete(ID);
    }

    @Test
    public void testSendWhenAddingNewRecord() throws Exception {
        //then
        verify(producer, times(1)).sendToConsumerWhenAddingNewRecord(CHEF_QUEUE, chefDto);
        verify(producer, times(1)).notifyAllSubscribersWhenAddingRecord(CHEF_TOPIC, chefDto);
    }

    @Test
    public void testSendWhenUpdatingNewRecord() throws Exception {
         //when
        chefDto.setName(CHEF_CONNOR);
        chefService.update(chefService.getChefsByName(CHEF_COLLIN).get(0).getId(), chefDto);

        //then
        verify(producer, times(1)).sendToConsumerWhenUpdatingRecord(CHEF_QUEUE, chefDto);
    }

    @Test
    public void testSendWhenDeletingNewRecord() throws Exception {
        //when
        chefService.delete(chefService.getChefsByName(CHEF_COLLIN).get(0).getId());

        //then
        verify(producer, times(1)).sendToConsumerWhenDeletingRecord(CHEF_QUEUE, chefDto);
    }
}
