package com.practice.cooking.converter;

import com.practice.cooking.dto.DishDto;
import com.practice.cooking.model.Dish;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DishEntityToDtoConverter implements Converter<Dish, DishDto> {

    @Override
    public DishDto convert(Dish source) {
        DishDto dishDto = DishDto.builder().build();
        if (source.getId() != null) {
            dishDto.setId(source.getId());
        }
        dishDto.setId(source.getId());
        dishDto.setName(source.getName());
        dishDto.setRecipe(source.getRecipe());
        return dishDto;
    }
}
