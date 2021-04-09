package com.practice.cooking.mapper;

import com.practice.cooking.dto.DishDto;
import com.practice.cooking.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DishDtoToEntityMapper {

    DishDtoToEntityMapper INSTANCE = Mappers.getMapper( DishDtoToEntityMapper.class );
    
    Dish dtoToEntity(DishDto dishDto);
    
}
