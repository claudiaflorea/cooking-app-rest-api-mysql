package com.practice.cooking.validator;

import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.exception.BadRequestException;
import com.practice.cooking.utils.CustomValidationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

@Component
public class RecipeDtoValidator implements SmartValidator {
    
    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        final RecipeDto recipeDto = (RecipeDto) target;
        if (recipeDto.getIngredients() != null ) {
            recipeDto.getIngredients().stream()
                .forEach(
                    ingredientDto -> {
                        CustomValidationUtils.validateIngredient(ingredientDto, errors);
                    }
                ); 
        }
        
        CustomValidationUtils.validateRecipe(recipeDto, errors);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RecipeDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
       throw new BadRequestException("RecipeDto.validation.requires.param");
    }
}
