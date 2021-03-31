package com.practice.cooking.validator;

import static java.lang.Character.isLowerCase;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;

@Component
public class DishDtoValidator implements SmartValidator {

//    private RecipeDtoValidator recipeDtoValidator;
//    
//    @Autowired
//    private void setRecipeDtoValidator(RecipeDtoValidator recipeDtoValidator) {
//        this.recipeDtoValidator = recipeDtoValidator;
//    }
    
    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        final DishDto dishDto = (DishDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "dishDto.name", "dish name is required");
        if (dishDto.getName().length() <= 2 || dishDto.getName().length() > 30) {
            errors.rejectValue("name", "dishDto.name", "dish name size must be greater than 2 chars and less than 30");
        }
        if (isLowerCase(dishDto.getName().charAt(0))) {
            errors.rejectValue("name", "dishDto.name", "dish name must start with capital letter");
        }
        if (dishDto.getRecipe() != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "dishDto.recipe.name","dish recipe name is required");
            if (dishDto.getRecipe().getName().length() <= 2 || dishDto.getRecipe().getName().length() > 30) {
                errors.rejectValue("name", "dishDto.recipe.name", "dish recipe name size must be greater than 2 chars and less than 30");
            }
            if (isLowerCase(dishDto.getRecipe().getName().charAt(0))) {
                errors.rejectValue("name", "dishDto.recipe.name","dish recipe name must start with capital letter");
            }
            if (dishDto.getRecipe().getCookingTime() < 0) {
                errors.rejectValue("cookingTime", "dishDto.recipe.cookingTime","dish recipe cookingTime size must be a positive number");
            }
            if (dishDto.getRecipe().getRecipeType() == null) {
                errors.rejectValue("recipeType", "dishDto.recipe.recipeType","dish recipeType must be provided");
            }
            if (dishDto.getRecipe().getIngredients() != null) {
                dishDto.getRecipe().getIngredients().stream()
                    .forEach(
                        ingredientDto -> {
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
                    );
            }
        }
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
