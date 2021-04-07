package com.practice.cooking.converter;

import com.practice.cooking.dto.RestaurantToDishDto;
import com.practice.cooking.model.RestaurantToDish;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantToDishEntityToDtoConverter implements Converter<RestaurantToDish, RestaurantToDishDto> {

    private final DishEntityToDtoConverter dishConverter;

    private final RestaurantEntityToDtoConverter restaurantConvverter;

    @Override
    public RestaurantToDishDto convert(RestaurantToDish source) {
        RestaurantToDishDto restaurantToDishDto = new RestaurantToDishDto();
        if (source.getId() != null) {
            restaurantToDishDto.setId(source.getId());
        }
        restaurantToDishDto.setDishDto(dishConverter.convert(source.getDish()));
        restaurantToDishDto.setRestaurantDto(restaurantConvverter.convert(source.getRestaurant()));
        restaurantToDishDto.setDishId(source.getDishId());
        restaurantToDishDto.setRestaurantId(source.getRestaurantId());
        return restaurantToDishDto;
    }
}
