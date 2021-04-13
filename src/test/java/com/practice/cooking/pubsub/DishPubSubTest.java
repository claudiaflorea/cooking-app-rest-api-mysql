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
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.mapper.DishDtoToEntityMapper;
import com.practice.cooking.mapper.DishEntityToDtoMapper;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.model.Unit;
import com.practice.cooking.publisher.Publisher;
import com.practice.cooking.repository.DishRepository;
import com.practice.cooking.repository.IngredientRepository;
import com.practice.cooking.repository.RecipeRepository;
import com.practice.cooking.service.DishService;
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
public class DishPubSubTest {

    private static final long   ID            = 1L;
    private static final String TARTAR_SAUCE  = "Tartar sauce";
    private static final String MAYONNAISE    = "Mayonnaise";
    private static final String RED_PEPPER    = "Red Pepper";
    private static final String YELLOW_PEPPER = "Yellow Pepper";
    private static final String TARTAR        = "Tartar";

    @Mock
    private DishRepository dishRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private Publisher producer;

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private DishDtoToEntityMapper dtoToEntityMapper;

    @Mock
    private DishEntityToDtoMapper entityToDtoMapper;

    @InjectMocks
    private DishService dishService;

    private static final Queue DISH_QUEUE   = new ActiveMQQueue("dish_queue");
    private static final Topic DISH_TOPIC   = new ActiveMQTopic("dish_topic");
    private static final Topic RECIPE_TOPIC = new ActiveMQTopic("recipe_topic");

    DishDto dishDto;
    Dish    dish;
    RecipeDto recipeDto;

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
        Recipe recipe = new Recipe(ID, TARTAR_SAUCE, Difficulty.MEDIUM, new HashSet<>(ingredientList), 1, RecipeType.SIDE);


        dishDto = new DishDto(ID, TARTAR_SAUCE, recipeDto);
        dish = new Dish(ID, TARTAR_SAUCE, ID, recipe, null);
        when(dishRepository.save(dish)).thenReturn(dish);
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(ingredientRepository.saveAll(asList(mayonnaise, redPepper, yellowPepper))).thenReturn(asList(mayonnaise, redPepper, yellowPepper));
        when(dishRepository.findById(ID)).thenReturn(java.util.Optional.ofNullable(dish));
        when(dishRepository.findAllByName(TARTAR_SAUCE)).thenReturn(Collections.singletonList(dish));
        when(dtoToEntityMapper.dtoToEntity(dishDto)).thenReturn(dish);
        when(entityToDtoMapper.entityToDto(dish)).thenReturn(dishDto);

        //when
        dishService.add(dishDto);
    }

    @AfterEach
    void clean() throws JsonProcessingException {
        ingredientService.delete(ID);
        recipeService.delete(ID);
        dishService.delete(ID);
    }

    @Test
    public void testSendWhenAddingNewRecord() throws Exception {
        //then
        verify(producer, times(1)).sendToConsumerWhenAddingNewRecord(DISH_QUEUE, dishDto);
        verify(producer, times(1)).notifyAllSubscribersWhenAddingRecord(DISH_TOPIC, dishDto);
        verify(producer, times(1)).notifyAllSubscribersWhenAddingRecord(RECIPE_TOPIC, recipeDto);
    }

    @Test
    public void testSendWhenUpdatingNewRecord() throws Exception {
        //when
        dishDto.setName(TARTAR);
        dishService.update(dishService.getAllByName(TARTAR_SAUCE).get(0).getId(), dishDto);

        //then
        verify(producer, times(1)).sendToConsumerWhenUpdatingRecord(DISH_QUEUE, dishDto);
    }

    @Test
    public void testSendWhenDeletingNewRecord() throws Exception {
        //when
        dishService.delete(dishService.getAllByName(TARTAR_SAUCE).get(0).getId());

        //then
        verify(producer, times(1)).sendToConsumerWhenDeletingRecord(DISH_QUEUE, dishDto);
    }
}
