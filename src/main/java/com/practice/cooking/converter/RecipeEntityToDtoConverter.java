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
public class RecipeEntityToDtoConverter implements Converter<Recipe, RecipeDto> {

    private final IngredientEntityToDtoConverter ingredientConverter;

    @Override
    public RecipeDto convert(Recipe source) {
        RecipeDto recipeDto = new RecipeDto();
        if (source.getId() != null) {
            recipeDto.setId(source.getId());
        }
        recipeDto.setId(source.getId());
        recipeDto.setName(source.getName());
        recipeDto.setDifficulty(source.getDifficulty());
        recipeDto.setCookingTime(source.getCookingTime());
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

}
