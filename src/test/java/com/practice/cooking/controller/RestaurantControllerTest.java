package com.practice.cooking.controller;

import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.service.RestaurantService;
import com.practice.cooking.utils.TestUtils;
import com.practice.cooking.validator.RestaurantDtoValidator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = RestaurantController.class)
@RunWith(SpringRunner.class)
public class RestaurantControllerTest {

    public static final long    RESTAURANT_ID           = 3L;
    public static final String  RESTAURANT_NAME         = "Casa Grande";
    public static final Integer RESTAURANT_STARS        = 5;
    public static final String  RESTAURANT_NAME_UPDATED = "Olive Garden";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private RestaurantService restaurantService;
    
    @MockBean
    private RestaurantDtoValidator restaurantDtoValidator;

    Restaurant restaurant = new Restaurant(RESTAURANT_ID, RESTAURANT_NAME, RESTAURANT_STARS, TestUtils.getDishList().stream().limit(2).collect(Collectors.toSet()), TestUtils.getChefList());

    @Test
    public void testGetRestaurantByIdWithValidParameters() throws Exception {
        String url = "/api/restaurants/{id}";

        when(restaurantService.getById(RESTAURANT_ID)).thenReturn(restaurant);

        mockMvc.perform(get(url, 3))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(RESTAURANT_ID))
            .andExpect(jsonPath("$.name").value(RESTAURANT_NAME))
            .andExpect(jsonPath("$.stars").value(RESTAURANT_STARS));
    }

    @Test
    public void testGetRestaurantByIdWithInvalidParameters() throws Exception {
        String url = "/api/restaurants/{id}";
        when(restaurantService.getById(RESTAURANT_ID)).thenReturn(restaurant);

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
    public void addRestaurantTest() throws Exception {
        String url = "/api/restaurants";
        
        when(restaurantDtoValidator.supports(RestaurantDto.class)).thenReturn(true);
        when(restaurantService.add(restaurant)).thenAnswer(
            (Answer<Restaurant>) invocation -> invocation.getArgument(0)
        );

        mockMvc.perform(post(url)
            .content(om.writeValueAsString(restaurant))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated());
    }

    @Test
    public void updateRestaurantTest() throws Exception {
        String url = "/api/restaurants/{id}";
        restaurant.setName(RESTAURANT_NAME_UPDATED);
        when(restaurantDtoValidator.supports(RestaurantDto.class)).thenReturn(true);

        mockMvc.perform(put(url, RESTAURANT_ID)
            .content(om.writeValueAsString(restaurant))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());
    }

    @Test
    public void deleteRestaurantTest() throws Exception {
        String url = "/api/restaurants/{id}";

        mockMvc.perform(delete(url, RESTAURANT_ID))
            .andExpect(status().isAccepted());
    }

}


