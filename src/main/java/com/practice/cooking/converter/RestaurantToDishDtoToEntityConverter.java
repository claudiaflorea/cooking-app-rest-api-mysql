package com.practice.cooking.converter;

import com.practice.cooking.dto.RestaurantToDishDto;
import com.practice.cooking.model.RestaurantToDish;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantToDishDtoToEntityConverter implements Converter<RestaurantToDishDto, RestaurantToDish> {

    private final DishDtoToEntityConverter dishConverter;

    private final RestaurantDtoToEntityConverter restaurantConvverter;

    @Override
    public RestaurantToDish convert(RestaurantToDishDto source) {
        RestaurantToDish restaurantToDish = new RestaurantToDish();
        if (source.getId() != null) {
            restaurantToDish.setId(source.getId());
        }
        restaurantToDish.setDish(dishConverter.convert(source.getDishDto()));
        restaurantToDish.setRestaurant(restaurantConvverter.convert(source.getRestaurantDto()));
        restaurantToDish.setDishId(source.getDishId());
        restaurantToDish.setRestaurantId(source.getRestaurantId());
        return restaurantToDish;
    }
}
