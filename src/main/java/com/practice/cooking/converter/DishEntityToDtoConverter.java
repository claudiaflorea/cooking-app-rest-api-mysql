package com.practice.cooking.converter;

import com.practice.cooking.dto.DishDto;
import com.practice.cooking.model.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishEntityToDtoConverter implements Converter<Dish, DishDto> {

    private final RecipeEntityToDtoConverter recipeEntityToDtoConverter;
    
    @Override
    public DishDto convert(Dish source) {
        DishDto dishDto = DishDto.builder().build();
        if (source.getId() != null) {
            dishDto.setId(source.getId());
        }
        dishDto.setId(source.getId());
        dishDto.setName(source.getName());
        if (source.getRecipe() != null) {
            dishDto.setRecipe(recipeEntityToDtoConverter.convert(source.getRecipe()));
        }
        return dishDto;
    }
}
