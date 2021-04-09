package com.practice.cooking.mapper;

import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.model.Chef;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChefDtoToEntityMapper {

    ChefDtoToEntityMapper INSTANCE = Mappers.getMapper( ChefDtoToEntityMapper.class );

    Chef dtoToEntity(ChefDto chefDto);
    
}
