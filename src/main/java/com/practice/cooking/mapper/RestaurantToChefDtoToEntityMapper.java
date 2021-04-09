package com.practice.cooking.mapper;

import com.practice.cooking.dto.RestaurantToChefDto;
import com.practice.cooking.model.RestaurantToChef;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantToChefDtoToEntityMapper {

    RestaurantToChefDtoToEntityMapper INSTANCE = Mappers.getMapper( RestaurantToChefDtoToEntityMapper.class );
    
    RestaurantToChef dtoToEntity(RestaurantToChefDto restaurantToChefDto);
    
}
