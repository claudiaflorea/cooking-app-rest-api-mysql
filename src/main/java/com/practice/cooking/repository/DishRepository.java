package com.practice.cooking.repository;

import java.util.List;

import com.practice.cooking.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    
    List<Dish> findAllByName(String name);
    
    //get all dishes with liquid ingredients
    @Query(value = "SELECT DISTINCT d FROM Dish d " +
        "LEFT JOIN FETCH d.recipe r " +
        "LEFT JOIN FETCH r.ingredients i " +
        "WHERE i.unit = 2")
    List<Dish> findAllByRecipeContainingLiquidIngredients();
       
}
