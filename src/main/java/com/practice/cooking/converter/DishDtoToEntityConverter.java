package com.practice.cooking.converter;

import com.practice.cooking.dto.DishDto;
import com.practice.cooking.model.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishDtoToEntityConverter implements Converter<DishDto, Dish> {
    
    private final RecipeDtoToEntityConverter recipeDtoToEntityConverter;
    
    @Override
    public Dish convert(DishDto source) {
        Dish dish = Dish.builder().build();
        if (source.getId() != null) {
            dish.setId(source.getId());
        }
        dish.setName(source.getName());
        dish.setRecipe(recipeDtoToEntityConverter.convert(source.getRecipe()));
        return dish;
    }
}
