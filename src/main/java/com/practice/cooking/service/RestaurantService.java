package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.repository.RestaurantRepository;
import com.practice.cooking.utils.DatabaseSequenceGenerator;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final MongoOperations mongoOperations;

    private final ChefService chefService;

    private final DishService dishService;

    private final DatabaseSequenceGenerator sequenceGenerator;

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant getById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant not found with id " + id));
    }

    public Restaurant add(Restaurant restaurant) {
        restaurant.setId(sequenceGenerator.generateSequence(Restaurant.SEQUENCE_NAME));
        for(Chef chef : restaurant.getChefs()) {
            if (chef != null && chef.getId() != null) {
                chefService.add(chef);
            }
        }
        for(Dish dish : restaurant.getDishes()) {
            if (dish != null && dish.getId() != null) {
                dishService.add(dish);
            }
        }
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    public Restaurant update(Long id, Restaurant restaurantDetails) {
        Restaurant restaurant = getById(id);
        restaurant.setStars(restaurantDetails.getStars());
        restaurant.setName(restaurantDetails.getName());
        restaurant.setChefs(restaurantDetails.getChefs());
        restaurant.setDishes(restaurantDetails.getDishes());
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    public Map<String, Boolean> delete(Long id) {
        Restaurant restaurant = getById(id);
        restaurantRepository.delete(restaurant);
        Map<String, Boolean> recipeMap = new HashMap<>();
        recipeMap.put("Recipe with id " + id + " is deleted ", Boolean.TRUE);
        return recipeMap;
    }

}
