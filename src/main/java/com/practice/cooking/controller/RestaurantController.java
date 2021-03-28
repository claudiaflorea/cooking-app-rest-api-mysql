package com.practice.cooking.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.practice.cooking.converter.RestaurantConverter;
import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.service.RestaurantService;
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
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    RestaurantConverter restaurantConverter;
    
    @GetMapping("/")
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantService.getAll()
            .stream()
            .map(restaurant -> toDTO(restaurant))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RestaurantDto getRestaurantById(@PathVariable(value="id") Long id) throws NotFoundException {
        return toDTO(restaurantService.getById(id));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRestaurant(@RequestBody RestaurantDto restaurant) {
        restaurantService.add(toEntity(restaurant));
    }

    @PutMapping("/{id}")
    public void updateRestaurant(@PathVariable(value = "id") Long id, @RequestBody RestaurantDto restaurantDetails) throws NotFoundException {
        restaurantService.update(id, toEntity(restaurantDetails));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRestaurant(@PathVariable(value = "id") Long id) throws NotFoundException {
        restaurantService.delete(id);
    }

    public Restaurant toEntity(RestaurantDto dto) {
        Restaurant entity = restaurantConverter.convertToEntity(dto);
        return entity;
    }

    public RestaurantDto toDTO(Restaurant entity) {
        RestaurantDto dto = restaurantConverter.convert(entity);
        return dto;
    }
}
