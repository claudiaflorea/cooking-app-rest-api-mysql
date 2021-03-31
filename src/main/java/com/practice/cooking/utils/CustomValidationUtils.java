package com.practice.cooking.utils;

import static java.lang.Character.isLowerCase;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@EnableConfigurationProperties(ErrorProperties.class)
@RequiredArgsConstructor
public class CustomValidationUtils {
    
    private final ErrorProperties properties;

    public static final String DTO_RECIPE_NAME = "dishDto.recipe.name";
    public static final String DTO_RECIPE_COOKING_TIME = "dishDto.recipe.cookingTime";
    public static final String DTO_RECIPE_RECIPE_TYPE = "dishDto.recipe.recipeType";
    public static final String SEPARATOR = " : ";
    public static final String INGREDIENT_DTO_NAME = "ingredientDto.name";
    public static final String INGREDIENT_DTO_QUANTITY = "ingredientDto.quantity";
    public static final String INGREDIENT_DTO_UNIT = "ingredientDto.unit";
    public static final String CHEF_DTO_NAME = "chefDto.name";
    public static final String DISH_DTO_NAME = "dishDto.name";
    public static final String RESTAURANT_DTO_NAME = "restaurantDto.name";
    public static final String RESTAURANT_DTO_STARS = "restaurantDto.stars";

    
    public void validateRecipe(RecipeDto recipeDto, Errors errors) {
        rejectIfEmptyOrWhitespace(errors, "name", DTO_RECIPE_NAME, DTO_RECIPE_NAME + SEPARATOR + properties.getRequired());
        if (recipeDto.getName().length() <= 2 || recipeDto.getName().length() > 30) {
            errors.rejectValue("name", DTO_RECIPE_NAME, DTO_RECIPE_NAME + SEPARATOR + properties.getSize());
        }
        if (isLowerCase(recipeDto.getName().charAt(0))) {
            errors.rejectValue("name", DTO_RECIPE_NAME, DTO_RECIPE_NAME + SEPARATOR + properties.getCapital());
        }
        if (recipeDto.getCookingTime() < 0) {
            errors.rejectValue("cookingTime", DTO_RECIPE_COOKING_TIME,DTO_RECIPE_COOKING_TIME + SEPARATOR + properties.getPositive());
        }
        if (recipeDto.getRecipeType() == null) {
            errors.rejectValue("recipeType", DTO_RECIPE_RECIPE_TYPE, DTO_RECIPE_RECIPE_TYPE + SEPARATOR + properties.getNotNull());
        }
    }
    
    public void validateIngredient(IngredientDto ingredientDto, Errors errors) {
        rejectIfEmptyOrWhitespace(errors, "name", INGREDIENT_DTO_NAME, INGREDIENT_DTO_NAME + SEPARATOR + properties.getRequired());
        if (ingredientDto.getName().length() <= 2 || ingredientDto.getName().length() > 30) {
            errors.rejectValue("name", INGREDIENT_DTO_NAME, INGREDIENT_DTO_NAME + SEPARATOR + properties.getSize());
        }
        if (isLowerCase(ingredientDto.getName().charAt(0))) {
            errors.rejectValue("name", INGREDIENT_DTO_NAME, INGREDIENT_DTO_NAME + SEPARATOR + properties.getCapital());
        }
        if (ingredientDto.getQuantity() < 0) {
            errors.rejectValue("quantity", INGREDIENT_DTO_QUANTITY, INGREDIENT_DTO_QUANTITY + SEPARATOR + properties.getPositive());
        }
        if (ingredientDto.getUnit() == null) {
            errors.rejectValue("unit", INGREDIENT_DTO_UNIT,INGREDIENT_DTO_UNIT + SEPARATOR + properties.getNotNull());
        }
    }
    
    public void validateChef(ChefDto chefDto, Errors errors) {
        rejectIfEmptyOrWhitespace(errors, "name", CHEF_DTO_NAME,CHEF_DTO_NAME + SEPARATOR + properties.getRequired());
        if (chefDto.getName().length() <= 2 || chefDto.getName().length() > 30) {
            errors.rejectValue("name", CHEF_DTO_NAME,CHEF_DTO_NAME + SEPARATOR + properties.getSize());
        }
        if (isLowerCase(chefDto.getName().charAt(0))) {
            errors.rejectValue("name", CHEF_DTO_NAME,CHEF_DTO_NAME + SEPARATOR + properties.getCapital());
        }
    }
    
    public void validateDish(DishDto dishDto, Errors errors) {
        rejectIfEmptyOrWhitespace(errors, "name", DISH_DTO_NAME, DISH_DTO_NAME + SEPARATOR + properties.getRequired());
        if (dishDto.getName().length() <= 2 || dishDto.getName().length() > 30) {
            errors.rejectValue("name", DISH_DTO_NAME, DISH_DTO_NAME + SEPARATOR + properties.getSize());
        }
        if (isLowerCase(dishDto.getName().charAt(0))) {
            errors.rejectValue("name", DISH_DTO_NAME, DISH_DTO_NAME + SEPARATOR + properties.getCapital());
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
    
    public void validateRestaurant(RestaurantDto restaurantDto, Errors errors) {
        rejectIfEmptyOrWhitespace(errors, "name", RESTAURANT_DTO_NAME,RESTAURANT_DTO_NAME + SEPARATOR + properties.getRequired());
        if (restaurantDto.getName().length() <= 2 || restaurantDto.getName().length() > 30) {
            errors.rejectValue("name", RESTAURANT_DTO_NAME, RESTAURANT_DTO_NAME + SEPARATOR + properties.getSize());
        }
        if (isLowerCase(restaurantDto.getName().charAt(0))) {
            errors.rejectValue("name", RESTAURANT_DTO_NAME, RESTAURANT_DTO_NAME + SEPARATOR + properties.getCapital());
        }
        if (restaurantDto.getStars() < 1 || restaurantDto.getStars() > 5) {
            errors.rejectValue("stars", RESTAURANT_DTO_STARS, RESTAURANT_DTO_NAME + SEPARATOR + properties.getRange() );
        }
    }
}
