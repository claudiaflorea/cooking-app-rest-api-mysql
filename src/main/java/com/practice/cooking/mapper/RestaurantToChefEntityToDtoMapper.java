package com.practice.cooking.mapper;

import com.practice.cooking.dto.RestaurantToChefDto;
import com.practice.cooking.model.RestaurantToChef;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantToChefEntityToDtoMapper {

    RestaurantToChefEntityToDtoMapper INSTANCE = Mappers.getMapper( RestaurantToChefEntityToDtoMapper.class );
    
    RestaurantToChefDto entityToDto(RestaurantToChef restaurantToChef);
    
}
