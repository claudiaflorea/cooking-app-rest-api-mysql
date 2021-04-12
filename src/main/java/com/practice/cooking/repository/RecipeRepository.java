package com.practice.cooking.repository;

import java.util.List;

import javax.persistence.Tuple;

import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByName(String name);

    List<Recipe> findAllByRecipeType(RecipeType recipeType);
    
    //find all recipes with avocado
    @Query(value = "SELECT * FROM recipes r INNER join ingredients i on i.i_r_id = r.r_id WHERE i.i_name = 'Avocado' ", nativeQuery = true)
    List<Recipe> findAllByIngredientsContainingAvocado();
    
    //find all recipes with carbohydrates
    @Query(value = "SELECT * FROM recipes r INNER JOIN ingredients i on i.i_r_id = r.r_id WHERE i.i_name IN ('Flour', 'Pasta', 'Rice', 'Bread')", nativeQuery = true)
    List<Tuple> findAllByIngredientsContainingCarbohydrates();

}