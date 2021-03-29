package com.practice.cooking.converter;

import com.practice.cooking.dto.DishDto;
import com.practice.cooking.model.Dish;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DishDtoToEntityConverter implements Converter<DishDto, Dish> {
    
    @Override
    public Dish convert(DishDto source) {
        Dish dish = new Dish();
        if (source.getId() != null) {
            dish.setId(source.getId());
        }
        dish.setName(source.getName());
        dish.setRecipe(source.getRecipe());
        return dish;
    }
}
