package com.practice.cooking.validator;

import static java.lang.Character.isLowerCase;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;

@Component
public class ChefDtoValidator implements SmartValidator {

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        final ChefDto chefDto = (ChefDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "chefDto.name","chef name is required");
        if (chefDto.getName().length() <= 2 || chefDto.getName().length() > 30) {
            errors.rejectValue("name", "chefDto.name", "chef name size must be greater than 2 chars and less than 30");
        }
        if (isLowerCase(chefDto.getName().charAt(0))) {
            errors.rejectValue("name", "chefDto.name","chef name must start with capital letter");
        }
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
