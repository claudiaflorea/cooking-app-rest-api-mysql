package com.practice.cooking.mapper;

import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantEntityToDtoMapper {

    RestaurantEntityToDtoMapper INSTANCE = Mappers.getMapper( RestaurantEntityToDtoMapper.class );
    
    RestaurantDto entityToDto(Restaurant restaurant);
    
}
