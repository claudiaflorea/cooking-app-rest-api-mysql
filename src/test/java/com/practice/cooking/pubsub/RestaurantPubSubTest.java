package com.practice.cooking.pubsub;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.jms.Queue;
import javax.jms.Topic;

import static com.practice.cooking.utils.TestUtils.createChefDto;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.mapper.RestaurantDtoToEntityMapper;
import com.practice.cooking.mapper.RestaurantEntityToDtoMapper;
import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.model.Unit;
import com.practice.cooking.publisher.Publisher;
import com.practice.cooking.repository.ChefRepository;
import com.practice.cooking.repository.DishRepository;
import com.practice.cooking.repository.IngredientRepository;
import com.practice.cooking.repository.RecipeRepository;
import com.practice.cooking.repository.RestaurantRepository;
import com.practice.cooking.service.ChefService;
import com.practice.cooking.service.DishService;
import com.practice.cooking.service.IngredientService;
import com.practice.cooking.service.RecipeService;
import com.practice.cooking.service.RestaurantService;
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
public class RestaurantPubSubTest {

    private static final long   ID            = 1L;
    private static final String TARTAR_SAUCE  = "Tartar sauce";
    private static final String MAYONNAISE    = "Mayonnaise";
    private static final String RED_PEPPER    = "Red Pepper";
    private static final String YELLOW_PEPPER = "Yellow Pepper";
    private static final String CASA_GRANDE   = "Casa Grande";
    private static final String CHEF_COLLIN   = "Chef. Collin";
    private static final String BIG_HOUSE     = "Big House";

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private ChefRepository chefRepository;

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
    private RestaurantDtoToEntityMapper dtoToEntityMapper;

    @Mock
    private RestaurantEntityToDtoMapper entityToDtoMapper;

    @Mock
    private DishService dishService;

    @Mock
    private ChefService chefService;

    @InjectMocks
    private RestaurantService restaurantService;

    private static final Queue RESTAURANT_QUEUE = new ActiveMQQueue("restaurant_queue");
    private static final Topic RESTAURANT_TOPIC = new ActiveMQTopic("restaurant_topic");
    private static final Topic CHEF_TOPIC       = new ActiveMQTopic("chef_topic");

    RestaurantDto restaurantDto;
    Restaurant    restaurant;
    ChefDto       chefDto;

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

        RecipeDto recipeDto = new RecipeDto(ID, TARTAR_SAUCE, Difficulty.MEDIUM, new HashSet<>(ingredientDtoList), 1, RecipeType.SIDE);
        Recipe recipe = new Recipe(ID, TARTAR_SAUCE, Difficulty.MEDIUM, new HashSet<>(ingredientList), 1, RecipeType.SIDE);

        DishDto dishDto = new DishDto(ID, TARTAR_SAUCE, recipeDto);
        Dish dish = new Dish(ID, TARTAR_SAUCE, ID, recipe, singleton(restaurant));

        chefDto = createChefDto(ID, CHEF_COLLIN);
        Chef chef = new Chef(ID, CHEF_COLLIN);

        restaurantDto = new RestaurantDto(ID, CASA_GRANDE, 4, singleton(dishDto), singleton(chefDto));
        restaurant = new Restaurant(ID, CASA_GRANDE, 4, singleton(dish), singleton(chef));


        when(dishRepository.save(dish)).thenReturn(dish);
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(chefRepository.save(chef)).thenReturn(chef);
        when(ingredientRepository.saveAll(asList(mayonnaise, redPepper, yellowPepper))).thenReturn(asList(mayonnaise, redPepper, yellowPepper));
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(restaurantRepository.findById(ID)).thenReturn(java.util.Optional.ofNullable(restaurant));
        when(restaurantRepository.findAllByName(CASA_GRANDE)).thenReturn(singletonList(restaurant));
        when(dtoToEntityMapper.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(entityToDtoMapper.entityToDto(restaurant)).thenReturn(restaurantDto);

        //when
        restaurantService.add(restaurantDto);
    }

    @AfterEach
    void clean() throws JsonProcessingException {
        chefService.delete(ID);
        ingredientService.deleteAll();
        recipeService.delete(ID);
        dishService.delete(ID);
        restaurantService.delete(ID);
    }

    @Test
    public void testSendWhenAddingNewRecord() throws Exception {
        //then
        verify(producer, times(1)).sendToConsumerWhenAddingNewRecord(RESTAURANT_QUEUE, restaurantDto);
        verify(producer, times(1)).notifyAllSubscribersWhenAddingRecord(RESTAURANT_TOPIC, restaurantDto);
        verify(producer, times(1)).notifyAllSubscribersWhenAddingRecord(CHEF_TOPIC, chefDto);
    }

    @Test
    public void testSendWhenUpdatingNewRecord() throws Exception {
        //when
        restaurantDto.setName(BIG_HOUSE);
        restaurantService.update(restaurantService.getAllByName(CASA_GRANDE).get(0).getId(), restaurantDto);

        //then
        verify(producer, times(1)).sendToConsumerWhenUpdatingRecord(RESTAURANT_QUEUE, restaurantDto);
    }

    @Test
    public void testSendWhenDeletingNewRecord() throws Exception {
        //when
        restaurantService.delete(restaurantService.getAllByName(CASA_GRANDE).get(0).getId());

        //then
        verify(producer, times(1)).sendToConsumerWhenDeletingRecord(RESTAURANT_QUEUE, restaurantDto);
    }
}
