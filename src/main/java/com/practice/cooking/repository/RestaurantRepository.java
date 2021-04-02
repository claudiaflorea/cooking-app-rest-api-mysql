package com.practice.cooking.repository;

import java.util.List;

import com.practice.cooking.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, Long> {
    
    List<Restaurant> findAllByName(String name);
    
    @Query("{'dishes.recipe.ingredients.name' : { $nin: ['Meat', 'Salami', 'Pancheta']}}")
    List<Restaurant> findAllByDishesNotContainingMeat();
}
