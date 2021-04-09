package com.practice.cooking.service;

import java.util.List;
import java.util.Optional;

import static com.practice.cooking.utils.TestUtils.createRestaurant;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.mapper.RestaurantDtoToEntityMapper;
import com.practice.cooking.mapper.RestaurantEntityToDtoMapper;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestaurantServiceTest {

    private static final String ALS_SEAFOOD = "Als Seafood";
    private static final String BON_APPETIT = "Bon appetit";

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantEntityToDtoMapper entityToDtoMapper;

    @Mock
    private RestaurantDtoToEntityMapper dtoToEntityMapper;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    public void testGetAllRestaurants() {
        Restaurant als = new Restaurant(1L, ALS_SEAFOOD, 3, null, null);
        Restaurant bonAppetit = new Restaurant(2L, BON_APPETIT, 2, null, null);

        when(restaurantRepository.findAll()).thenReturn(asList(als, bonAppetit));
        when(entityToDtoMapper.entityToDto(als)).thenReturn(new RestaurantDto(1L, ALS_SEAFOOD, 3, null, null));
        when(entityToDtoMapper.entityToDto(bonAppetit)).thenReturn(new RestaurantDto(2L, BON_APPETIT, 2, null, null));

        List<RestaurantDto> restaurants = restaurantService.getAll();

        checkInitialRestaurantsList(restaurants);
    }

    private void checkInitialRestaurantsList(List<RestaurantDto> restaurants) {
        assertEquals(restaurants.size(), 2);
        assertAll("List of restaurants",
            () -> assertEquals(ALS_SEAFOOD, restaurants.get(0).getName()),
            () -> assertEquals(BON_APPETIT, restaurants.get(1).getName())
        );
    }

    @Test
    public void testSaveAndGetRestaurantById() {
        Restaurant newAddedRestaurant = new Restaurant(6L, ALS_SEAFOOD, 4, null, null);

        when(entityToDtoMapper.entityToDto(newAddedRestaurant)).thenReturn(new RestaurantDto(6L, ALS_SEAFOOD, 4, null, null));
        when(restaurantRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newAddedRestaurant));

        restaurantRepository.save(newAddedRestaurant);
        RestaurantDto retrievedRestaurant = restaurantService.getById(6L);

        //here we verify if the record was saved in the DB, 
        //and also if the getById method works
        assertEquals(retrievedRestaurant.getName(), newAddedRestaurant.getName());
        assertEquals(retrievedRestaurant.getStars(), newAddedRestaurant.getStars());
    }

    @Test
    public void testDeleteRestaurant() {
        Restaurant restaurant = createRestaurant(BON_APPETIT, 2);
        restaurant.setId(1L);
        RestaurantDto restaurantDto = new RestaurantDto(1L, BON_APPETIT, 2, null, null);

        when(entityToDtoMapper.entityToDto(restaurant)).thenReturn(restaurantDto);
        when(dtoToEntityMapper.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.ofNullable(restaurant));

        restaurantService.delete(restaurant.getId());
        Mockito.verify(restaurantRepository, Mockito.atLeastOnce()).delete(restaurant);
    }

}
