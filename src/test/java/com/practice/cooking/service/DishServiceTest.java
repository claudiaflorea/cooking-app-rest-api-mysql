package com.practice.cooking.service;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.mapper.DishDtoToEntityMapper;
import com.practice.cooking.mapper.DishEntityToDtoMapper;
import com.practice.cooking.model.Dish;
import com.practice.cooking.publisher.Publisher;
import com.practice.cooking.repository.DishRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DishServiceTest {

    private static final String APPLE_PIE = "Apple pie";
    private static final String RISOTTO   = "Risotto";
    private static final String BOLOGNESE = "Bolognese";

    @Mock
    private DishRepository dishRepository;

    @Mock
    private Publisher publisher;

    @Mock
    private DishEntityToDtoMapper entityToDtoMapper;

    @Mock
    private DishDtoToEntityMapper dtoToEntityMapper;

    @InjectMocks
    private DishService dishService;

    @Test
    public void testGetAllDishes() {
        Dish applePie = new Dish(1L, APPLE_PIE, null, null, null);
        Dish risotto = new Dish(2L, RISOTTO, null, null, null);

        when(dishRepository.findAll()).thenReturn(asList(applePie, risotto));
        when(entityToDtoMapper.entityToDto(applePie)).thenReturn(new DishDto(1L, APPLE_PIE, null));
        when(entityToDtoMapper.entityToDto(risotto)).thenReturn(new DishDto(2L, RISOTTO, null));

        List<DishDto> dishes = dishService.getAll();

        checkInitialDishList(dishes);
    }

    private void checkInitialDishList(List<DishDto> dishes) {
        assertEquals(dishes.size(), 2);
        assertAll("List of dishes",
            () -> assertEquals(APPLE_PIE, dishes.get(0).getName()),
            () -> assertEquals(RISOTTO, dishes.get(1).getName())
        );
    }

    @Test
    public void testSaveAndGetDishById() {
        Dish newAddedDish = new Dish();
        newAddedDish.setId(6L);
        newAddedDish.setName(BOLOGNESE);
        when(dishRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newAddedDish));
        when(entityToDtoMapper.entityToDto(newAddedDish)).thenReturn(new DishDto(6L, BOLOGNESE, null));

        dishRepository.save(newAddedDish);
        DishDto retrievedDish = dishService.getById(6L);

        //here we verify if the record was saved in the DB, 
        //and also if the getById method works
        assertEquals(retrievedDish.getName(), newAddedDish.getName());
    }

    @Test
    public void testDeleteDish() throws JsonProcessingException {
        Dish dish = new Dish();
        dish.setId(10L);
        dish.setName(BOLOGNESE);
        
        DishDto dishDto = new DishDto();
        dishDto.setId(10L);
        dishDto.setName(BOLOGNESE);

        when(dtoToEntityMapper.dtoToEntity(dishDto)).thenReturn(dish);
        when(entityToDtoMapper.entityToDto(dish)).thenReturn(dishDto);
        when(dishRepository.findById(10L)).thenReturn(Optional.of(dish));

        dishService.delete(dish.getId());
        Mockito.verify(dishRepository, Mockito.atLeastOnce()).delete(dish);
    }

}
