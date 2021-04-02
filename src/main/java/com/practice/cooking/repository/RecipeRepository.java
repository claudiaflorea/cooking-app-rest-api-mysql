package com.practice.cooking.repository;

import java.util.List;

import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, Long> {

    List<Recipe> findAllByName(String name);

    List<Recipe> findAllByRecipeType(RecipeType recipeType);
    
    //find all recipes with avocado
    @Query("{'ingredients.name' : 'Avocado' }")
    List<Recipe> findAllByIngredientsContainingAvocado();

}