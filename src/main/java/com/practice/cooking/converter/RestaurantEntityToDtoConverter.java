package com.practice.cooking.converter;

import java.util.stream.Collectors;

import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantEntityToDtoConverter implements Converter<Restaurant, RestaurantDto> {

    private final ChefEntityToDtoConverter chefConverter;

    private final DishEntityToDtoConverter dishConverter;

    @Override
    public RestaurantDto convert(Restaurant source) {
        RestaurantDto restaurantDto = RestaurantDto.builder().build();
        if (source.getId() != null) {
            restaurantDto.setId(source.getId());
        }
        restaurantDto.setId(source.getId());
        restaurantDto.setName(source.getName());
        restaurantDto.setStars(source.getStars());
        if (source.getChefs() != null) {
            restaurantDto.setChefs(
                source.getChefs().stream()
                    .map(chef -> chefConverter.convert(chef))
                    .collect(Collectors.toSet())
            );
        }
        if (source.getDishes() != null) {
            restaurantDto.setDishes(
                source.getDishes()
                    .stream()
                    .map(dish -> dishConverter.convert(dish))
                    .collect(Collectors.toSet())
            );
        }
        return restaurantDto;
    }

}
