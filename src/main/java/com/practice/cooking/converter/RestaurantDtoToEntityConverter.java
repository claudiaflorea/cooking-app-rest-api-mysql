package com.practice.cooking.converter;

import java.util.stream.Collectors;

import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.model.Restaurant;
import com.sun.org.apache.regexp.internal.RE;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantDtoToEntityConverter implements Converter<RestaurantDto, Restaurant> {
    
    private final ChefDtoToEntityConverter chefConverter;
    
    private final DishDtoToEntityConverter dishConverter;

    @Override
    public Restaurant convert(RestaurantDto source) {
        Restaurant restaurant = new Restaurant();
        if (source.getId() != null) {
            restaurant.setId(source.getId());
        }
        restaurant.setId(source.getId());
        restaurant.setName(source.getName());
        restaurant.setStars(source.getStars());
        if (source.getChefs() != null) {
            restaurant.setChefs(
                source.getChefs()
                    .stream()
                    .map(chef -> chefConverter.convert(chef))
                    .collect(Collectors.toList())
            );
        }
        if (source.getDishes() != null) {
            restaurant.setDishes(
                source.getDishes()
                    .stream()
                    .map(dish -> dishConverter.convert(dish))
                    .collect(Collectors.toList())
            );
        }
        return restaurant;
    }

}
