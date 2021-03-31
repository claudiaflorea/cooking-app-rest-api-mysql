package com.practice.cooking.validator;

import static java.lang.Character.isLowerCase;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Component
public class RecipeDtoValidator implements SmartValidator {
    
    // COULD NOT INJECT IngredientDtoValidator neither using constructor, setter or field, or initBinder
//    private IngredientDtoValidator ingredientDtoValidator = new IngredientDtoValidator();
//    
//    @InitBinder
//    public void initBinder(WebDataBinder dataBinder) {
//        dataBinder.addValidators(ingredientDtoValidator);
//    }
    
    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        final RecipeDto recipeDto = (RecipeDto) target;
        if (recipeDto.getIngredients() != null ) {
            recipeDto.getIngredients().stream()
                .forEach(
                    ingredient -> {
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "ingredient.name","ingredient name is required");
                        if (ingredient.getName().length() <= 2 || ingredient.getName().length() > 30) {
                            errors.rejectValue("name", "ingredient.name", "name size must be greater than 2 chars and less than 30");
                        }
                        if (isLowerCase(ingredient.getName().charAt(0))) {
                            errors.rejectValue("name", "ingredient.name","name must start with capital letter");
                        }
                        if (ingredient.getQuantity() < 0) {
                            errors.rejectValue("quantity", "ingredient.quantity","quantity must be a positive number");
                        }
                        if (ingredient.getUnit() == null) {
                            errors.rejectValue("unit", "ingredient.unit","unit must be provided");
                        }
                    }
                ); 
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "recipeDto.name","recipe name is required");
        if (recipeDto.getName().length() <= 2 || recipeDto.getName().length() > 30) {
            errors.rejectValue("name", "recipeDto.name", "recipe name size must be greater than 2 chars and less than 30");
        }
        if (isLowerCase(recipeDto.getName().charAt(0))) {
            errors.rejectValue("name", "recipeDto.name","recipe name must start with capital letter");
        }
        if (recipeDto.getCookingTime() < 0) {
            errors.rejectValue("cookingTime", "recipeDto.cookingTime","recipe cookingTime size must be a positive number");
        }
        if (recipeDto.getRecipeType() == null) {
            errors.rejectValue("recipeType", "recipeDto.recipeType","recipeType must be provided");
        }
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
