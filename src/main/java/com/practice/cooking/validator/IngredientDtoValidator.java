package com.practice.cooking.validator;

import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.exception.BadRequestException;
import com.practice.cooking.utils.CustomValidationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

@Component
public class IngredientDtoValidator implements SmartValidator {

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        final IngredientDto ingredientDto = (IngredientDto) target;
        CustomValidationUtils.validateIngredient(ingredientDto, errors);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return IngredientDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
       throw new BadRequestException("IngredientDto.validation.requires.param");
    }
}
