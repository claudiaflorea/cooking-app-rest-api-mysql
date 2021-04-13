package com.practice.cooking.pubsub;

import java.util.Collections;
import javax.jms.Queue;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.mapper.IngredientDtoToEntityMapper;
import com.practice.cooking.mapper.IngredientEntityToDtoMapper;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Unit;
import com.practice.cooking.publisher.Publisher;
import com.practice.cooking.repository.IngredientRepository;
import com.practice.cooking.service.IngredientService;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class IngredientPubSubTest {

    private static final long   ID         = 1L;
    private static final String MAYONNAISE = "Mayonnaise";
    private static final String MAYO       = "Mayo";

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private Publisher producer;

    @Mock
    private IngredientDtoToEntityMapper dtoToEntityMapper;

    @Mock
    private IngredientEntityToDtoMapper entityToDtoMapper;

    @InjectMocks
    private IngredientService ingredientService;

    private static final Queue INGREDIENT_QUEUE = new ActiveMQQueue("ingredient_queue");

    IngredientDto mayonnaiseDto;
    Ingredient    mayonnaise;

    @BeforeEach
    void init() throws JsonProcessingException {
        //given
        mayonnaiseDto = new IngredientDto(ID, MAYONNAISE, 0.5, Unit.KG);
        mayonnaise = new Ingredient(ID, MAYONNAISE, 0.5, Unit.KG);

        when(ingredientRepository.save(mayonnaise)).thenReturn(mayonnaise);
        when(ingredientRepository.findById(ID)).thenReturn(java.util.Optional.ofNullable(mayonnaise));
        when(ingredientRepository.findAllByName(MAYONNAISE)).thenReturn(Collections.singletonList(mayonnaise));
        when(dtoToEntityMapper.dtoToEntity(mayonnaiseDto)).thenReturn(mayonnaise);
        when(entityToDtoMapper.entityToDto(mayonnaise)).thenReturn(mayonnaiseDto);

        //when
        ingredientService.add(mayonnaiseDto);
    }

    @AfterEach
    void clean() throws JsonProcessingException {
        ingredientService.delete(ID);
    }

    @Test
    public void testSendWhenAddingNewRecord() throws Exception {
        //then
        verify(producer, times(1)).sendToConsumerWhenAddingNewRecord(INGREDIENT_QUEUE, mayonnaiseDto);
    }

    @Test
    public void testSendWhenUpdatingNewRecord() throws Exception {
        //when
        mayonnaiseDto.setName(MAYO);
        ingredientService.update(ingredientService.getAllByName(MAYONNAISE).get(0).getId(), mayonnaiseDto);

        //then
        verify(producer, times(1)).sendToConsumerWhenUpdatingRecord(INGREDIENT_QUEUE, mayonnaiseDto);
    }

    @Test
    public void testSendWhenDeletingNewRecord() throws Exception {
        //when
        ingredientService.delete(ingredientService.getAllByName(MAYONNAISE).get(0).getId());

        //then
        verify(producer, times(1)).sendToConsumerWhenDeletingRecord(INGREDIENT_QUEUE, mayonnaiseDto);
    }
}
