package com.practice.cooking.converter;

import java.util.stream.Collectors;

import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeConverter implements Converter<Recipe, RecipeDto> {

    @Autowired
    private IngredientConverter ingredientConverter;

    @Override
    public RecipeDto convert(Recipe source) {
        RecipeDto recipeDto = new RecipeDto();
        if (source.getId() != null) {
            recipeDto.setId(source.getId());
        }
        recipeDto.setId(source.getId());
        recipeDto.setName(source.getName());
        recipeDto.setDifficulty(source.getDifficulty());
        recipeDto.setRecipeType(source.getRecipeType());
        if (source.getIngredients() != null) {
            recipeDto.setIngredients(
                source.getIngredients()
                    .stream()
                    .map(ingredient -> ingredientConverter.convert(ingredient))
                    .collect(Collectors.toList())
            );
        }
        return recipeDto;
    }

    public Recipe convertToEntity(RecipeDto source) {
        Recipe recipe = new Recipe();
        if (source.getId() != null) {
            recipe.setId(source.getId());
        }
        recipe.setName(source.getName());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setRecipeType(source.getRecipeType());
        if (source.getIngredients() != null) {
            recipe.setIngredients(
                source.getIngredients()
                    .stream()
                    .map(ingredientDto -> ingredientConverter.convertToEntity(ingredientDto))
                    .collect(Collectors.toList())
            );
        }
        return recipe;
    }
}
