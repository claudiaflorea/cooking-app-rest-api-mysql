package com.practice.cooking.mapper;

import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.model.Chef;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChefEntityToDtoMapper {

    ChefEntityToDtoMapper INSTANCE = Mappers.getMapper( ChefEntityToDtoMapper.class );
    
    ChefDto entityToDto(Chef chef);
    
}
