package com.practice.cooking.validator;

import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.exception.BadRequestException;
import com.practice.cooking.utils.CustomValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

@Component
public class RecipeDtoValidator implements SmartValidator {
    
    private CustomValidationUtils customValidationUtils;

    @Autowired
    public void setCustomValidationUtils(CustomValidationUtils customValidationUtils) {
        this.customValidationUtils = customValidationUtils;
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        final RecipeDto recipeDto = (RecipeDto) target;
        if (recipeDto.getIngredients() != null ) {
            recipeDto.getIngredients().stream()
                .forEach(
                    ingredientDto -> {
                        customValidationUtils.validateIngredient(ingredientDto, errors);
                    }
                ); 
        }

       // customValidationUtils.validateRecipe(recipeDto, errors);
        customValidationUtils.validateSmoothieRecipe(recipeDto, errors);
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
