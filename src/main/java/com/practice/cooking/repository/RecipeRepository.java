package com.practice.cooking.repository;

import java.util.List;

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
    @Query(value = "SELECT r FROM Recipe r LEFT JOIN FETCH Ingredient i WHERE i.name = 'Avocado' ")
    List<Recipe> findAllByIngredientsContainingAvocado();

}