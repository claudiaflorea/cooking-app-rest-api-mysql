package com.practice.cooking.converter;

import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.model.Chef;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChefEntityToDtoConverter implements Converter<Chef, ChefDto> {

    @Override
    public ChefDto convert(Chef source) {
        ChefDto chefDtoBuilder = ChefDto.builder().build();
        if (source.getId() != null) {
            chefDtoBuilder.setId(source.getId());
        }
        chefDtoBuilder.setId(source.getId());
        chefDtoBuilder.setName(source.getName());
        return chefDtoBuilder;
    }
}
