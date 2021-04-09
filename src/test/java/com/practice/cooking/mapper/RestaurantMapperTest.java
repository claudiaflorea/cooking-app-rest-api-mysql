package com.practice.cooking.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.practice.cooking.utils.TestUtils.getChefList;
import static com.practice.cooking.utils.TestUtils.getDishList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestaurantMapperTest {

    public static final Long RESTAURANT_ID = 6L;
    public static final String RESTAURANT_NAME = "Als Seafood";
    public static final Integer STARS = 5;
   
    @Autowired
    private RestaurantEntityToDtoMapper entityToDtoMapper;

    @Autowired
    private RestaurantDtoToEntityMapper dtoToEntityMapper;

    @Autowired
    private ChefEntityToDtoMapper chefEntityToDtoMapper;

    @Autowired
    private DishEntityToDtoMapper dishEntityToDtoMapper;
        
    @Test
    public void testRestaurantToDtoConversion() {
        Restaurant restaurant = new Restaurant(RESTAURANT_ID, RESTAURANT_NAME, STARS, new HashSet<>(getDishList()), getChefList());
        RestaurantDto restaurantDto = entityToDtoMapper.entityToDto(restaurant);

        assertAll("RestaurantDto mapped object",
            () -> assertEquals(RESTAURANT_ID, restaurantDto.getId()),
            () -> assertEquals(RESTAURANT_NAME, restaurantDto.getName()),
            () -> assertEquals(STARS, restaurantDto.getStars()),
            () -> assertEquals("Apple pie", restaurantDto.getDishes().stream().sorted().collect(Collectors.toList()).get(0).getName()),
            () -> assertEquals("Eugene", restaurantDto.getChefs().stream().sorted().collect(Collectors.toList()).get(0).getName())
            );
    }

    @Test
    public void testRestaurantDtoToEntityConversion() {
        List<DishDto> dishDtoList = new ArrayList<>();
        for (Dish dish : getDishList()) {
            dishDtoList.add(dishEntityToDtoMapper.entityToDto(dish));
        }

        List<ChefDto> chefDtoList = new ArrayList<>();
        for (Chef chef : getChefList()) {
            chefDtoList.add(chefEntityToDtoMapper.entityToDto(chef));
        }
        
        RestaurantDto restaurantDto = new RestaurantDto(RESTAURANT_ID, RESTAURANT_NAME, STARS, new TreeSet<>(dishDtoList), new TreeSet<>(chefDtoList));
        Restaurant restaurant = dtoToEntityMapper.dtoToEntity(restaurantDto);
        
        assertAll("Restaurant mapped object",
            () -> assertEquals(RESTAURANT_ID, restaurant.getId()),
            () -> assertEquals(RESTAURANT_NAME, restaurant.getName()),
            () -> assertEquals(STARS, restaurant.getStars()),
            () -> assertEquals("Apple pie", restaurant.getDishes().stream().sorted().collect(Collectors.toList()).get(0).getName()),
            () -> assertEquals("Eugene", restaurant.getChefs().stream().sorted().collect(Collectors.toList()).get(0).getName())
        );
    }
}
