package com.practice.cooking.converter;

import com.practice.cooking.dto.RestaurantToChefDto;
import com.practice.cooking.model.RestaurantToChef;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantToChefEntityToDtoConverter implements Converter<RestaurantToChef, RestaurantToChefDto> {

    private final ChefEntityToDtoConverter chefConverter;

    private final RestaurantEntityToDtoConverter restaurantConvverter;

    @Override
    public RestaurantToChefDto convert(RestaurantToChef source) {
        RestaurantToChefDto restaurantToChefDto = new RestaurantToChefDto();
        if (source.getId() != null) {
            restaurantToChefDto.setId(source.getId());
        }
        restaurantToChefDto.setChefDto(chefConverter.convert(source.getChef()));
        restaurantToChefDto.setRestaurantDto(restaurantConvverter.convert(source.getRestaurant()));
        restaurantToChefDto.setChefId(source.getChefId());
        restaurantToChefDto.setRestaurantId(source.getRestaurantId());
        return restaurantToChefDto;
    }
}
