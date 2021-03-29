package com.practice.cooking.converter;

import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.model.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Component;

@Component
public class IngredientConverter implements Converter<Ingredient, IngredientDto> {
    
    @Override
    public IngredientDto convert(Ingredient source) {
        IngredientDto ingredientDto = new IngredientDto();
        if (source.getId() != null) {
            ingredientDto.setId(source.getId());
        }
        ingredientDto.setId(source.getId());
        ingredientDto.setName(source.getName());
        ingredientDto.setQuantity(source.getQuantity());
        ingredientDto.setUnit(source.getUnit());
        return ingredientDto;
    }
    
    public Ingredient convertToEntity(IngredientDto source) {
        Ingredient ingredient = new Ingredient();
        if (source.getId() != null) {
            ingredient.setId(source.getId());
        }
        ingredient.setName(source.getName());
        ingredient.setQuantity(source.getQuantity());
        ingredient.setUnit(source.getUnit());
        return ingredient;
    }
}
