package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.repository.IngredientRepository;
import com.practice.cooking.repository.RecipeRepository;
import com.practice.cooking.utils.DatabaseSequenceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final MongoOperations mongoOperations;

    private final DatabaseSequenceGenerator sequenceGenerator;
    
    private final IngredientService ingredientService;
    
    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    public Recipe getById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new NotFoundException("Recipe not found with id " + id));
    }

    public Recipe add(Recipe recipe) {
        recipe.setId(sequenceGenerator.generateSequence(Recipe.SEQUENCE_NAME));
        if (recipe.getIngredients() != null) {
            for (Ingredient ingredient: recipe.getIngredients()) {
                if (ingredient != null && ingredient.getId() != null) {
                    ingredientService.add(ingredient);
                }
            }
        }
        return recipeRepository.save(recipe);
    }

    public Recipe update(Long id, Recipe recipeDetails) {
        Recipe recipe = getById(id);
        recipe.setName(recipeDetails.getName());
        recipe.setCookingTime(recipeDetails.getCookingTime());
        recipe.setDifficulty(recipeDetails.getDifficulty());
        recipe.setRecipeType(recipeDetails.getRecipeType());
        recipe.setIngredients(recipeDetails.getIngredients());
        recipeRepository.save(recipe);
        return recipe;
    }

    public Map<String, Boolean> delete(Long id) {
        Recipe recipe = getById(id);
        recipeRepository.delete(recipe);
        Map<String, Boolean> recipeMap = new HashMap<>();
        recipeMap.put("Recipe with id " + id + " is deleted ", Boolean.TRUE);
        return recipeMap;
    }
}
