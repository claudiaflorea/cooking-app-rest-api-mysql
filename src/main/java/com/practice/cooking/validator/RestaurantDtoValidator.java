package com.practice.cooking.validator;

import static java.lang.Character.isLowerCase;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;

@Component
public class RestaurantDtoValidator implements SmartValidator {

//    private ChefDtoValidator chefDtoValidator;
//
//    private DishDtoValidator dishDtoValidator;
//
//    @Autowired
//    public void setChefDtoValidator(ChefDtoValidator chefDtoValidator) {
//        this.chefDtoValidator = chefDtoValidator;
//    }
//
//    @Autowired
//    public void setDishDtoValidator(DishDtoValidator dishDtoValidator) {
//        this.dishDtoValidator = dishDtoValidator;
//    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        final RestaurantDto restaurantDto = (RestaurantDto) target;
        if (restaurantDto.getChefs() != null) {
            restaurantDto.getChefs().stream()
                .forEach(
                    chefDto -> {
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "chefDto.name","chef name is required");
                        if (chefDto.getName().length() <= 2 || chefDto.getName().length() > 30) {
                            errors.rejectValue("name", "chefDto.name", "chef name size must be greater than 2 chars and less than 30");
                        }
                        if (isLowerCase(chefDto.getName().charAt(0))) {
                            errors.rejectValue("name", "chefDto.name","chef name must start with capital letter");
                        }
                    }
                );
        }
       
        if (restaurantDto.getDishes() != null) {
            restaurantDto.getDishes().stream()
                .forEach(
                    dishDto -> {
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
                        }
                    }
                );
        }
        
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

    @Override
    public boolean supports(Class<?> clazz) {
        return RestaurantDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
       throw new BadRequestException("RestaurantDto.validation.requires.param");
    }
}
