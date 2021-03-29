package com.practice.cooking.converter;

import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.model.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientEntityToDtoConverter implements Converter<Ingredient, IngredientDto> {
    
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

}
