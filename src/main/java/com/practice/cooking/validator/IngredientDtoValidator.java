package com.practice.cooking.validator;

import static java.lang.Character.isLowerCase;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;

@Component
public class IngredientDtoValidator implements SmartValidator {

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        final IngredientDto ingredientDto = (IngredientDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "ingredientDto.name","ingredient name is required");
        if (ingredientDto.getName().length() <= 2 || ingredientDto.getName().length() > 30) {
            errors.rejectValue("name", "ingredientDto.name", "ingredient name size must be greater than 2 chars and less than 20");
        }
        if (isLowerCase(ingredientDto.getName().charAt(0))) {
            errors.rejectValue("name", "ingredientDto.name","ingredient name must start with capital letter");
        }
        if (ingredientDto.getQuantity() < 0) {
            errors.rejectValue("quantity", "ingredientDto.quantity","ingredient quantity must be a positive number");
        }
        if (ingredientDto.getUnit() == null) {
            errors.rejectValue("unit", "ingredientDto.unit","ingredient unit must be provided");
        }
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
