package com.practice.cooking.validator;

import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.exception.BadRequestException;
import com.practice.cooking.utils.CustomValidationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

@Component
public class RestaurantDtoValidator implements SmartValidator {

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        final RestaurantDto restaurantDto = (RestaurantDto) target;
        if (restaurantDto.getChefs() != null) {
            restaurantDto.getChefs().stream()
                .forEach(
                    chefDto -> {
                        CustomValidationUtils.validateChef(chefDto, errors);
                    }
                );
        }
       
        if (restaurantDto.getDishes() != null) {
            restaurantDto.getDishes().stream()
                .forEach(
                    dishDto -> {
                        CustomValidationUtils.validateDish(dishDto, errors);
                    }
                );
        }
        
       CustomValidationUtils.validateRestaurant(restaurantDto, errors);
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
