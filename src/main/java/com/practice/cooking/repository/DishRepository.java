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
    @Query(value = "SELECT * FROM dishes d inner join recipes r on d.d_r_id = r.r_id inner join ingredients i on r.r_id = i.i_r_id WHERE i.i_unit = 2", nativeQuery = true)
//    @Query(value = "SELECT d FROM Dish d " +
//        "LEFT JOIN FETCH d.recipe r " +
//        "LEFT JOIN FETCH r.ingredients i " +
//        "WHERE i.unit = 2")
    List<Dish> findAllByRecipeContainingLiquidIngredients();
       
}
