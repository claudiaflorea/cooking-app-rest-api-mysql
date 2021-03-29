package com.practice.cooking.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.practice.cooking.converter.RestaurantEntityToDtoConverter;
import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final ConversionService conversionService;

    @GetMapping()
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantService.getAll()
            .stream()
            .map(restaurant -> conversionService.convert(restaurant, RestaurantDto.class))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RestaurantDto getRestaurantById(@PathVariable(value = "id") Long id) throws NotFoundException {
        return conversionService.convert(restaurantService.getById(id), RestaurantDto.class);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createRestaurant(@RequestBody RestaurantDto restaurant) {
        restaurantService.add(conversionService.convert(restaurant, Restaurant.class));
    }

    @PutMapping("/{id}")
    public void updateRestaurant(@PathVariable(value = "id") Long id, @RequestBody RestaurantDto restaurantDetails) throws NotFoundException {
        restaurantService.update(id, conversionService.convert(restaurantDetails, Restaurant.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRestaurant(@PathVariable(value = "id") Long id) throws NotFoundException {
        restaurantService.delete(id);
    }
    
}
