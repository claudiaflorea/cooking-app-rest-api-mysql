package com.practice.cooking.converter;

import com.practice.cooking.dto.RestaurantToChefDto;
import com.practice.cooking.model.RestaurantToChef;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantToChefDtoToEntityConverter implements Converter<RestaurantToChefDto, RestaurantToChef> {

    private final ChefDtoToEntityConverter chefConverter;

    private final RestaurantDtoToEntityConverter restaurantConvverter;

    @Override
    public RestaurantToChef convert(RestaurantToChefDto source) {
        RestaurantToChef restaurantToChef = new RestaurantToChef();
        if (source.getId() != null) {
            restaurantToChef.setId(source.getId());
        }
        restaurantToChef.setChef(chefConverter.convert(source.getChefDto()));
        restaurantToChef.setRestaurant(restaurantConvverter.convert(source.getRestaurantDto()));
        restaurantToChef.setChefId(source.getChefId());
        restaurantToChef.setRestaurantId(source.getRestaurantId());
        return restaurantToChef;
    }
}
