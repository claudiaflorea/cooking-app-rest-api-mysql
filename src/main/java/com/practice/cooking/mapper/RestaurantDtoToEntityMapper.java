package com.practice.cooking.mapper;

import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantDtoToEntityMapper {

    RestaurantDtoToEntityMapper INSTANCE = Mappers.getMapper( RestaurantDtoToEntityMapper.class );
    
    Restaurant dtoToEntity(RestaurantDto restaurantDto);
    
}
