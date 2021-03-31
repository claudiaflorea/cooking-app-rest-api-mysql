package com.practice.cooking.validator;

import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.exception.BadRequestException;
import com.practice.cooking.utils.CustomValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

@Component
public class RestaurantDtoValidator implements SmartValidator {

    private CustomValidationUtils customValidationUtils;

    @Autowired
    public void setCustomValidationUtils(CustomValidationUtils customValidationUtils) {
        this.customValidationUtils = customValidationUtils;
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        final RestaurantDto restaurantDto = (RestaurantDto) target;
        if (restaurantDto.getChefs() != null) {
            restaurantDto.getChefs().stream()
                .forEach(
                    chefDto -> {
                        customValidationUtils.validateChef(chefDto, errors);
                    }
                );
        }
       
        if (restaurantDto.getDishes() != null) {
            restaurantDto.getDishes().stream()
                .forEach(
                    dishDto -> {
                        customValidationUtils.validateDish(dishDto, errors);
                    }
                );
        }

        customValidationUtils.validateRestaurant(restaurantDto, errors);
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
