package com.practice.cooking.service;

import java.util.List;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.repository.IngredientRepository;
import com.practice.cooking.repository.RecipeRepository;
import com.practice.cooking.utils.DatabaseSequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    private MongoOperations mongoOperations;

    @Autowired
    private DatabaseSequenceGenerator sequenceGenerator;
    
    @Autowired
    private IngredientService ingredientService;

    @Autowired
    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Autowired
    public void setRecipeRepository(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

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
                if (ingredient != null) {
                    ingredientService.add(ingredient);
                }
            }
        }
        return recipeRepository.save(recipe);
    }

    public void update(Long id, Recipe recipeDetails) {
        Recipe recipe = getById(id);
        recipe.setName(recipeDetails.getName());
        recipe.setCookingTime(recipeDetails.getCookingTime());
        recipe.setDifficulty(recipeDetails.getDifficulty());
        recipe.setRecipeType(recipeDetails.getRecipeType());
        recipe.setIngredients(recipeDetails.getIngredients());
        recipeRepository.save(recipe);
    }

    public void delete(Long id) {
        Recipe recipe = getById(id);
        recipeRepository.delete(recipe);
    }
}
