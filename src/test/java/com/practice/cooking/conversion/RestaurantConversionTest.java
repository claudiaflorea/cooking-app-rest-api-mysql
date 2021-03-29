package com.practice.cooking.conversion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.practice.cooking.utils.TestUtils.getChefList;
import static com.practice.cooking.utils.TestUtils.getDishList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.converter.ChefConverter;
import com.practice.cooking.converter.DishConverter;
import com.practice.cooking.converter.RestaurantConverter;
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
public class RestaurantConversionTest {

    public static final Long RESTAURANT_ID = 6L;
    public static final String RESTAURANT_NAME = "Als Seafood";
    public static final Integer STARS = 5;
    
    @Autowired
    private RestaurantConverter restaurantConverter;
    
    @Autowired
    private DishConverter dishConverter;
    
    @Autowired
    private ChefConverter chefConverter;
    
    @Test
    public void testRestaurantToDtoConversion() {
        Restaurant restaurant = new Restaurant(RESTAURANT_ID, RESTAURANT_NAME, STARS, getDishList(), getChefList());
        RestaurantDto restaurantDto = restaurantConverter.convert(restaurant);

        assertAll("RestaurantDto mapped object",
            () -> assertEquals(RESTAURANT_ID, restaurantDto.getId()),
            () -> assertEquals(RESTAURANT_NAME, restaurantDto.getName()),
            () -> assertEquals(STARS, restaurantDto.getStars()),
            () -> assertEquals("Apple pie", restaurantDto.getDishes().get(0).getName()),
            () -> assertEquals("Eugene", restaurantDto.getChefs().get(0).getName())
            );
    }

    @Test
    public void testRestaurantDtoToEntityConversion() {
        List<DishDto> dishDtoList = new ArrayList<>();
        for (Dish dish : getDishList()) {
            dishDtoList.add(dishConverter.convert(dish));
        }

        List<ChefDto> chefDtoList = new ArrayList<>();
        for (Chef chef : getChefList()) {
            chefDtoList.add(chefConverter.convert(chef));
        }
        
        RestaurantDto restaurantDto = new RestaurantDto(RESTAURANT_ID, RESTAURANT_NAME, STARS, dishDtoList, chefDtoList);
        Restaurant restaurant = restaurantConverter.convertToEntity(restaurantDto);

        assertAll("Restaurant mapped object",
            () -> assertEquals(RESTAURANT_ID, restaurantDto.getId()),
            () -> assertEquals(RESTAURANT_NAME, restaurantDto.getName()),
            () -> assertEquals(STARS, restaurantDto.getStars()),
            () -> assertEquals("Apple pie", restaurantDto.getDishes().get(0).getName()),
            () -> assertEquals("Eugene", restaurantDto.getChefs().get(0).getName())
        );
    }
}
