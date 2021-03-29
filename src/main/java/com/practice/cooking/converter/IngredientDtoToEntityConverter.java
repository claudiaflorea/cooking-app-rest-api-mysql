package com.practice.cooking.converter;

import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.model.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientDtoToEntityConverter implements Converter<IngredientDto, Ingredient> {
        
    public Ingredient convert(IngredientDto source) {
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
