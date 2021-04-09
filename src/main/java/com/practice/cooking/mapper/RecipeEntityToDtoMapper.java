package com.practice.cooking.mapper;

import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecipeEntityToDtoMapper {

    RecipeEntityToDtoMapper INSTANCE = Mappers.getMapper( RecipeEntityToDtoMapper.class );
    
    RecipeDto entityToDto(Recipe recipe);
    
}
