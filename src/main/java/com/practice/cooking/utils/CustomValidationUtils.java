package com.practice.cooking.utils;

import static java.lang.Character.isLowerCase;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.dto.RestaurantDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class CustomValidationUtils {
    
    public static void validateRecipe(RecipeDto recipeDto, Errors errors) {
        org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "dishDto.recipe.name","dish recipe name is required");
        if (recipeDto.getName().length() <= 2 || recipeDto.getName().length() > 30) {
            errors.rejectValue("name", "dishDto.recipe.name", "dish recipe name size must be greater than 2 chars and less than 30");
        }
        if (isLowerCase(recipeDto.getName().charAt(0))) {
            errors.rejectValue("name", "dishDto.recipe.name","dish recipe name must start with capital letter");
        }
        if (recipeDto.getCookingTime() < 0) {
            errors.rejectValue("cookingTime", "dishDto.recipe.cookingTime","dish recipe cookingTime size must be a positive number");
        }
        if (recipeDto.getRecipeType() == null) {
            errors.rejectValue("recipeType", "dishDto.recipe.recipeType","dish recipeType must be provided");
        }
    }
    
    public static void validateIngredient(IngredientDto ingredientDto, Errors errors) {
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
    
    public static void validateChef(ChefDto chefDto, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "chefDto.name","chef name is required");
        if (chefDto.getName().length() <= 2 || chefDto.getName().length() > 30) {
            errors.rejectValue("name", "chefDto.name", "chef name size must be greater than 2 chars and less than 30");
        }
        if (isLowerCase(chefDto.getName().charAt(0))) {
            errors.rejectValue("name", "chefDto.name","chef name must start with capital letter");
        }
    }
    
    public static void validateDish(DishDto dishDto, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "dishDto.name", "dish name is required");
        if (dishDto.getName().length() <= 2 || dishDto.getName().length() > 30) {
            errors.rejectValue("name", "dishDto.name", "dish name size must be greater than 2 chars and less than 30");
        }
        if (isLowerCase(dishDto.getName().charAt(0))) {
            errors.rejectValue("name", "dishDto.name", "dish name must start with capital letter");
        }
        if (dishDto.getRecipe() != null) {
            validateRecipe(dishDto.getRecipe(), errors);
            if (dishDto.getRecipe().getIngredients() != null) {
                dishDto.getRecipe().getIngredients().stream()
                    .forEach(
                        ingredientDto -> {
                            validateIngredient(ingredientDto, errors);
                        }
                    );
            }
        }
    }
    
    public static void validateRestaurant(RestaurantDto restaurantDto, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "restaurantDto.name","restaurant name is required");
        if (restaurantDto.getName().length() <= 2 || restaurantDto.getName().length() > 30) {
            errors.rejectValue("name", "restaurantDto.name", "restaurant name size must be greater than 2 chars and less than 30");
        }
        if (isLowerCase(restaurantDto.getName().charAt(0))) {
            errors.rejectValue("name", "restaurantDto.name","restaurant name must start with capital letter");
        }
        if (restaurantDto.getStars() < 1 || restaurantDto.getStars() > 5) {
            errors.rejectValue("stars", "restaurantDto.stars","restaurant stars must be in range 1-5");
        }
    }
}
