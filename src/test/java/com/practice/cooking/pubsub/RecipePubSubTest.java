package com.practice.cooking.pubsub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.jms.Queue;
import javax.jms.Topic;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.mapper.RecipeDtoToEntityMapper;
import com.practice.cooking.mapper.RecipeEntityToDtoMapper;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.model.Unit;
import com.practice.cooking.publisher.Publisher;
import com.practice.cooking.repository.IngredientRepository;
import com.practice.cooking.repository.RecipeRepository;
import com.practice.cooking.service.IngredientService;
import com.practice.cooking.service.RecipeService;
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
public class RecipePubSubTest {

    private static final long   ID            = 1L;
    private static final String TARTAR_SAUCE  = "Tartar sauce";
    private static final String MAYONNAISE    = "Mayonnaise";
    private static final String RED_PEPPER    = "Red Pepper";
    private static final String YELLOW_PEPPER = "Yellow Pepper";
    private static final String TARTAR        = "Tartar";

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private Publisher producer;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private RecipeDtoToEntityMapper dtoToEntityMapper;

    @Mock
    private RecipeEntityToDtoMapper entityToDtoMapper;

    @InjectMocks
    private RecipeService recipeService;

    private static final Queue RECIPE_QUEUE = new ActiveMQQueue("recipe_queue");
    private static final Topic RECIPE_TOPIC = new ActiveMQTopic("recipe_topic");

    RecipeDto recipeDto;
    Recipe    recipe;

    @BeforeEach
    void init() throws JsonProcessingException {
        //given
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        IngredientDto mayonnaiseDto = new IngredientDto(ID, MAYONNAISE, 0.5, Unit.KG);
        IngredientDto redPepperDto = new IngredientDto(ID, RED_PEPPER, 1, Unit.KG);
        IngredientDto yellowPepperDto = new IngredientDto(ID, YELLOW_PEPPER, 1, Unit.KG);
        ingredientDtoList.add(mayonnaiseDto);
        ingredientDtoList.add(redPepperDto);
        ingredientDtoList.add(yellowPepperDto);

        List<Ingredient> ingredientList = new ArrayList<>();
        Ingredient mayonnaise = new Ingredient(ID, MAYONNAISE, 0.5, Unit.KG);
        Ingredient redPepper = new Ingredient(ID, RED_PEPPER, 1, Unit.KG);
        Ingredient yellowPepper = new Ingredient(ID, YELLOW_PEPPER, 1, Unit.KG);
        ingredientList.add(mayonnaise);
        ingredientList.add(redPepper);
        ingredientList.add(yellowPepper);

        recipeDto = new RecipeDto(ID, TARTAR_SAUCE, Difficulty.MEDIUM, new HashSet<>(ingredientDtoList), 1, RecipeType.SIDE);
        recipe = new Recipe(ID, TARTAR_SAUCE, Difficulty.MEDIUM, new HashSet<>(ingredientList), 1, RecipeType.SIDE);

        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(ingredientRepository.saveAll(asList(mayonnaise, redPepper, yellowPepper))).thenReturn(asList(mayonnaise, redPepper, yellowPepper));
        when(recipeRepository.findById(ID)).thenReturn(java.util.Optional.ofNullable(recipe));
        when(recipeRepository.findAllByName(TARTAR_SAUCE)).thenReturn(Collections.singletonList(recipe));
        when(dtoToEntityMapper.dtoToEntity(recipeDto)).thenReturn(recipe);
        when(entityToDtoMapper.entityToDto(recipe)).thenReturn(recipeDto);

        //when
        recipeService.add(recipeDto);
    }

    @AfterEach
    void clean() throws JsonProcessingException {
        ingredientService.deleteAll();
        recipeService.delete(ID);
    }

    @Test
    public void testSendWhenAddingNewRecord() throws Exception {
        //then
        verify(producer, times(1)).sendToConsumerWhenAddingNewRecord(RECIPE_QUEUE, recipeDto);
        verify(producer, times(1)).notifyAllSubscribersWhenAddingRecord(RECIPE_TOPIC, recipeDto);
    }

    @Test
    public void testSendWhenUpdatingNewRecord() throws Exception {
        //when
        recipeDto.setName(TARTAR);
        recipeService.update(recipeService.getAllByName(TARTAR_SAUCE).get(0).getId(), recipeDto);

        //then
        verify(producer, times(1)).sendToConsumerWhenUpdatingRecord(RECIPE_QUEUE, recipeDto);
    }

    @Test
    public void testSendWhenDeletingNewRecord() throws Exception {
        //when
        recipeService.delete(recipeService.getAllByName(TARTAR_SAUCE).get(0).getId());

        //then
        verify(producer, times(1)).sendToConsumerWhenDeletingRecord(RECIPE_QUEUE, recipeDto);
    }
}
