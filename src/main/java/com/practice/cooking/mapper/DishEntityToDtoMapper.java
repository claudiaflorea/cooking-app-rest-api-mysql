package com.practice.cooking.mapper;

import com.practice.cooking.dto.DishDto;
import com.practice.cooking.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DishEntityToDtoMapper {

    DishEntityToDtoMapper INSTANCE = Mappers.getMapper( DishEntityToDtoMapper.class );
    
    DishDto entityToDto(Dish dish);
    
}
