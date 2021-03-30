package com.practice.cooking.converter;

import java.util.stream.Collectors;

import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeDtoToEntityConverter implements Converter<RecipeDto, Recipe> {

    private final IngredientDtoToEntityConverter ingredientConverter;
    
    @Override
    public Recipe convert(RecipeDto source) {
        Recipe recipe = Recipe.builder().build();
        if (source.getId() != null) {
            recipe.setId(source.getId());
        }
        recipe.setName(source.getName());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setCookingTime(source.getCookingTime());
        recipe.setRecipeType(source.getRecipeType());
        if (source.getIngredients() != null) {
            recipe.setIngredients(
                source.getIngredients().stream()
                    .map(ingredientDto -> ingredientConverter.convert(ingredientDto))
                    .collect(Collectors.toList())
            );
        }
        return recipe;
    }
}
