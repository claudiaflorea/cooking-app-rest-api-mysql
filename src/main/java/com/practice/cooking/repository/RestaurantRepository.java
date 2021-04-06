package com.practice.cooking.repository;

import java.util.List;

import com.practice.cooking.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
    List<Restaurant> findAllByName(String name);
   
    @Query(value = "SELECT r FROM Restaurant r " +
        "LEFT JOIN FETCH Dish d " +
        "LEFT JOIN FETCH Ingredient i " +
        "WHERE i.name NOT IN ('Meat', 'Salami', 'Pancheta')")
    List<Restaurant> findAllByDishesNotContainingMeat();
}
