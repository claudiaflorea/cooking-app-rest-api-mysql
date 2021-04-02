package com.practice.cooking.repository;

import java.util.List;

import com.practice.cooking.model.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends MongoRepository<Dish, Long> {
    
    List<Dish> findAllByName(String name);
    
    //get all dishes with liquid ingredients
    @Query("{'recipe.ingredients.unit' : 'LITER'}")
    List<Dish> findAllByRecipeContainingLiquidIngredients();
       
}
