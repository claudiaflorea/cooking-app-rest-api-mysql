package com.practice.cooking.mapper;

import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.model.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IngredientDtoToEntityMapper {

    IngredientDtoToEntityMapper INSTANCE = Mappers.getMapper( IngredientDtoToEntityMapper.class );
    
    Ingredient dtoToEntity(IngredientDto ingredientDto);
    
}
