package com.practice.cooking.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.model.Dish;
import com.practice.cooking.repository.DishRepository;
import com.practice.cooking.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = DishServiceTest.DishServiceTestConfig.class)
public class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private DishService dishService;

    @Test
    public void testGetAllDishes() {
        Mockito.when(dishRepository.findAll()).thenReturn(TestUtils.getDishList());

        List<Dish> dishes = dishService.getAll();

        checkInitialDishList(dishes);
    }

    private void checkInitialDishList(List<Dish> dishes) {
        assertEquals(dishes.size(), 5);
        assertAll("List of dishes",
            () -> assertEquals("Apple pie", dishes.get(0).getName()),
            () -> assertEquals("Risotto", dishes.get(1).getName()),
            () -> assertEquals("Mac'n'cheese", dishes.get(2).getName()),
            () -> assertEquals("Brownie", dishes.get(3).getName()),
            () -> assertEquals("Coleslaw", dishes.get(4).getName())
        );
    }

    @Test
    public void testSaveAndGetDishById() {
        Dish newAddedDish = new Dish(6L, "Bolognese");
        Mockito.when(dishRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newAddedDish));

        dishRepository.save(newAddedDish);
        Dish retrievedDish = (Dish) dishService.getById(6L);

        //here we verify if the record was saved in the DB, 
        //and also if the getById method works
        assertEquals(retrievedDish, newAddedDish);
    }

    @Test
    public void testDeleteChef() {
        Mockito.when(dishRepository.findAll()).thenReturn(TestUtils.getDishList());

        List<Dish> dishes = dishService.getAll();
        Mockito.when(dishRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(dishes.get(0)));

        checkInitialDishList(dishes);
        dishService.delete(dishes.get(0).getId());
        Mockito.verify(dishRepository, Mockito.atLeastOnce()).delete(dishes.get(0));
    }

    @Configuration
    public static class DishServiceTestConfig {

        @Bean
        public DishRepository dishRepository() {
            return Mockito.mock(DishRepository.class);
        }

        @Bean
        public MongoOperations mongoOperations() {
            return Mockito.mock(MongoOperations.class);
        }
    }
}
