package com.practice.cooking.converter;

import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.model.Chef;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChefDtoToEntityConverter implements Converter<ChefDto, Chef> {

    @Override
    public Chef convert(ChefDto source) {
        Chef chef = Chef.builder().build();
        if (source.getId() != null) {
            chef.setId(source.getId());
        }
        chef.setName(source.getName());
        return chef;
    }
}
