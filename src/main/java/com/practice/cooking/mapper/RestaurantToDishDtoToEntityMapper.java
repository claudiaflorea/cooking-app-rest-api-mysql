package com.practice.cooking.mapper;

import com.practice.cooking.dto.RestaurantToDishDto;
import com.practice.cooking.model.RestaurantToDish;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantToDishDtoToEntityMapper {

    RestaurantToDishDtoToEntityMapper INSTANCE = Mappers.getMapper( RestaurantToDishDtoToEntityMapper.class );
    
    RestaurantToDish dtoToEntity(RestaurantToDishDto restaurantToDishDto);
    
}

