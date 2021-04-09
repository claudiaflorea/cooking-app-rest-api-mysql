package com.practice.cooking.mapper;

import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecipeDtoToEntityMapper {

    RecipeDtoToEntityMapper INSTANCE = Mappers.getMapper( RecipeDtoToEntityMapper.class );
    
    Recipe dtoToEntity(RecipeDto recipeDto);
    
}
