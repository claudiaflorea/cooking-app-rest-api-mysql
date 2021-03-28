package com.practice.cooking.converter;

import java.util.stream.Collectors;

import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RestaurantConverter implements Converter<Restaurant, RestaurantDto> {

    @Autowired
    private ChefConverter chefConverter;

    @Autowired
    private DishConverter dishConverter;

    @Override
    public RestaurantDto convert(Restaurant source) {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(source.getId());
        restaurantDto.setName(source.getName());
        restaurantDto.setStars(source.getStars());
        restaurantDto.setChefs(
            source.getChefs()
                .stream()
                .map(chef -> chefConverter.convert(chef))
                .collect(Collectors.toList())
        );
        restaurantDto.setDishes(
            source.getDishes()
                .stream()
                .map(dish -> dishConverter.convert(dish))
                .collect(Collectors.toList())
        );
        return restaurantDto;
    }

    public Restaurant convertToEntity(RestaurantDto source) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(source.getId());
        restaurant.setName(source.getName());
        restaurant.setStars(source.getStars());
        restaurant.setChefs(
            source.getChefs()
                .stream()
                .map(chef -> chefConverter.convertToEntity(chef))
                .collect(Collectors.toList())
        );
        restaurant.setDishes(
            source.getDishes()
                .stream()
                .map(dish -> dishConverter.convertToEntity(dish))
                .collect(Collectors.toList())
        );
        return restaurant;
    }
}
