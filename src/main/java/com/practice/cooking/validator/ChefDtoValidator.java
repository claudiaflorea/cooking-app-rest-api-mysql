package com.practice.cooking.validator;

import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.exception.BadRequestException;
import com.practice.cooking.utils.CustomValidationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

@Component
public class ChefDtoValidator implements SmartValidator {

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        final ChefDto chefDto = (ChefDto) target;
        CustomValidationUtils.validateChef(chefDto, errors);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ChefDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
       throw new BadRequestException("chefDto.validation.requires.param");
    }
}
