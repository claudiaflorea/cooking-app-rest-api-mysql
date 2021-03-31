package com.practice.cooking.validator;

import com.practice.cooking.dto.DishDto;
import com.practice.cooking.exception.BadRequestException;
import com.practice.cooking.utils.CustomValidationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

@Component
public class DishDtoValidator implements SmartValidator {
    
    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        final DishDto dishDto = (DishDto) target;
        CustomValidationUtils.validateDish(dishDto, errors);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DishDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        throw new BadRequestException("DishDto.validation.requires.param");
    }
}
