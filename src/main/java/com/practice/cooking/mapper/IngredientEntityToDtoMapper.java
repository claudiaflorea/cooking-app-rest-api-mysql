package com.practice.cooking.mapper;

import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.model.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IngredientEntityToDtoMapper {

    IngredientEntityToDtoMapper INSTANCE = Mappers.getMapper( IngredientEntityToDtoMapper.class );
    
    IngredientDto entityToDto(Ingredient ingredient);
    
}
