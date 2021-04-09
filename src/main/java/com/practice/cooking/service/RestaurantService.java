package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.mapper.RestaurantDtoToEntityMapper;
import com.practice.cooking.mapper.RestaurantEntityToDtoMapper;
import com.practice.cooking.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final ChefService chefService;

    private final DishService dishService;

    private final RestaurantDtoToEntityMapper dtoToEntityMapper;

    private final RestaurantEntityToDtoMapper entityToDtoMapper;

    public List<RestaurantDto> getAll() {
        return restaurantRepository.findAll().stream()
            .map(restaurant -> entityToDtoMapper.entityToDto(restaurant))
            .collect(Collectors.toList());
    }

    public RestaurantDto getById(Long id) {
        return restaurantRepository.findById(id)
            .map(restaurant -> entityToDtoMapper.entityToDto(restaurant))
            .orElseThrow(() -> new NotFoundException("Restaurant not found with id " + id));
    }

    public RestaurantDto add(RestaurantDto restaurant) {
        for(ChefDto chef : restaurant.getChefs()) {
            if (chef != null && chef.getId() != null) {
                chefService.add(chef);
            }
        }
        for(DishDto dish : restaurant.getDishes()) {
            if (dish != null && dish.getId() != null) {
                dishService.add(dish);
            }
        }
        restaurantRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(restaurant)));
        return restaurant;
    }

    public RestaurantDto update(Long id, RestaurantDto restaurantDetails) {
        RestaurantDto restaurant = getById(id);
        restaurant.setStars(restaurantDetails.getStars());
        restaurant.setName(restaurantDetails.getName());
        restaurant.setChefs(restaurantDetails.getChefs());
        restaurant.setDishes(restaurantDetails.getDishes());
        restaurantRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(restaurant)));
        return restaurant;
    }

    public Map<String, Boolean> delete(Long id) {
        RestaurantDto restaurant = getById(id);
        restaurantRepository.delete(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(restaurant)));
        Map<String, Boolean> recipeMap = new HashMap<>();
        recipeMap.put("Recipe with id " + id + " is deleted ", Boolean.TRUE);
        return recipeMap;
    }
    
}
