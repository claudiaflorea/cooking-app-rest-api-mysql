package com.practice.cooking.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.repository.RestaurantRepository;
import com.practice.cooking.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = RestaurantServiceTest.RestaurantServiceTestConfig.class)
public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    public void testGetAllRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(TestUtils.getRestaurantList());

        List<Restaurant> restaurants = restaurantService.getAll();

        checkInitialRestaurantsList(restaurants);
    }

    private void checkInitialRestaurantsList(List<Restaurant> restaurants) {
        assertEquals(restaurants.size(), 2);
        assertAll("List of restaurants",
            () -> assertEquals("Als Seafood", restaurants.get(0).getName()),
            () -> assertEquals("Bon appetit", restaurants.get(1).getName())
        );
    }

    @Test
    public void testSaveAndGetRestaurantById() {
        Restaurant newAddedRestaurant = new Restaurant(6L, "Yum", 4, TestUtils.getDishList(), TestUtils.getChefList());
        when(restaurantRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newAddedRestaurant));

        restaurantRepository.save(newAddedRestaurant);
        Restaurant retrievedRestaurant = restaurantService.getById(6L);

        //here we verify if the record was saved in the DB, 
        //and also if the getById method works
        assertEquals(retrievedRestaurant, newAddedRestaurant);
    }

    @Test
    public void testDeleteRestaurant() {
        when(restaurantRepository.findAll()).thenReturn(TestUtils.getRestaurantList());

        List<Restaurant> restaurants = restaurantService.getAll();
        when(restaurantRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(restaurants.get(0)));

        checkInitialRestaurantsList(restaurants);
        restaurantService.delete(restaurants.get(0).getId());
        Mockito.verify(restaurantRepository, Mockito.atLeastOnce()).delete(restaurants.get(0));
    }

    @Configuration
    public static class RestaurantServiceTestConfig {

        @Bean
        public RestaurantRepository restaurantRepository() {
            return Mockito.mock(RestaurantRepository.class);
        }

    }
}
