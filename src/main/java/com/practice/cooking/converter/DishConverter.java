package com.practice.cooking.converter;

import com.practice.cooking.dto.DishDto;
import com.practice.cooking.model.Dish;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DishConverter implements Converter<Dish, DishDto> {

    @Override
    public DishDto convert(Dish source) {
        DishDto dishDto = new DishDto();
        dishDto.setId(source.getId());
        dishDto.setName(source.getName());
        dishDto.setRecipe(source.getRecipe());
        return dishDto;
    }
    
    public Dish convertToEntity(DishDto source) {
        Dish dish = new Dish();
        dish.setId(source.getId());
        dish.setName(source.getName());
        dish.setRecipe(source.getRecipe());
        return dish;
    }
}
