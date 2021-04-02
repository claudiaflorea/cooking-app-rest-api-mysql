package com.practice.cooking.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.service.DishService;
import com.practice.cooking.validator.DishDtoValidator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = DishController.class)
@RunWith(SpringRunner.class)
public class DishControllerTest {

    public static final long   DISH_ID           = 10L;
    public static final String DISH_NAME         = "Pasta";
    public static final String DISH_NAME_UPDATED = "Spaghetti";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private DishService dishService;
    
    @MockBean
    private DishDtoValidator dishDtoValidator;

    Recipe recipe = new Recipe(DISH_ID, DISH_NAME, Difficulty.MEDIUM, null, 1, RecipeType.MAIN_COURSE);
    Dish   dish   = new Dish(DISH_ID, DISH_NAME, recipe);

    @Test
    public void testGetDishByIdWithValidParameters() throws Exception {
        String url = "/api/dishes/{id}";
        
        when(dishService.getById(DISH_ID)).thenReturn(dish);

        mockMvc.perform(get(url, 10))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(DISH_ID))
            .andExpect(jsonPath("$.name").value(DISH_NAME))
            .andExpect(jsonPath("$.recipe.name").value(DISH_NAME));
    }

    @Test
    public void testGetDishByIdWithInvalidParameters() throws Exception {
        String url = "/api/dishes/{id}";
        when(dishService.getById(DISH_ID)).thenReturn(dish);

        mockMvc.perform(get(url, "/??"))
            .andExpect(status().is5xxServerError());
    }

    @Test
    public void testGetWithInvalidPath() throws Exception {
        String url = "/api";

        mockMvc.perform(get(url, "/??"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void addDishTest() throws Exception {
        String url = "/api/dishes";
        when(dishDtoValidator.supports(DishDto.class)).thenReturn(true);
        when(dishService.add(dish)).thenAnswer(
            (Answer<Dish>) invocation -> invocation.getArgument(0)
        );

        mockMvc.perform(post(url)
            .content(om.writeValueAsString(dish))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated());
    }

    @Test
    public void updateDishTest() throws Exception {
        String url = "/api/dishes/{id}";
        dish.setName(DISH_NAME_UPDATED);
        when(dishDtoValidator.supports(DishDto.class)).thenReturn(true);

        mockMvc.perform(put(url, DISH_ID)
            .content(om.writeValueAsString(dish))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());
    }

    @Test
    public void deleteDishTest() throws Exception {
        String url = "/api/dishes/{id}";

        mockMvc.perform(delete(url, DISH_ID))
            .andExpect(status().isAccepted());
    }

}


