package com.practice.cooking.service;

import java.util.List;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.repository.RestaurantRepository;
import com.practice.cooking.utils.DatabaseSequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    private MongoOperations mongoOperations;

    @Autowired
    private ChefService chefService;

    @Autowired
    private DishService dishService;

    @Autowired
    private DatabaseSequenceGenerator sequenceGenerator;

    @Autowired
    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Autowired
    public void setRestaurantRepository(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant getById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant not found with id " + id));
    }

    public Restaurant add(Restaurant restaurant) {
        restaurant.setId(sequenceGenerator.generateSequence(Restaurant.SEQUENCE_NAME));
        for(Chef chef : restaurant.getChefs()) {
            chefService.add(chef);
        }
        for(Dish dish : restaurant.getDishes()) {
            dishService.add(dish);
        }
        return restaurantRepository.save(restaurant);
    }

    public void update(Long id, Restaurant restaurantDetails) {
        Restaurant restaurant = getById(id);
        restaurant.setStars(restaurantDetails.getStars());
        restaurant.setName(restaurantDetails.getName());
        restaurant.setChefs(restaurantDetails.getChefs());
        restaurant.setDishes(restaurantDetails.getDishes());
        restaurantRepository.save(restaurant);
    }

    public void delete(Long id) {
        Restaurant restaurant = getById(id);
        restaurantRepository.delete(restaurant);
    }

}
