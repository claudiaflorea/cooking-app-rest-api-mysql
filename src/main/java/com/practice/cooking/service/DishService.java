package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.practice.cooking.dto.DishDto;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    private final RecipeService recipeService;

    private final ConversionService conversionService;
    
    public List<DishDto> getAll() {
        return dishRepository.findAll().stream()
            .map(dish -> conversionService.convert(dish, DishDto.class))
            .collect(Collectors.toList());
    }

    public DishDto getById(Long id) {
        return dishRepository.findById(id)
            .map(dish -> conversionService.convert(dish, DishDto.class))
            .orElseThrow(() -> new NotFoundException("Dish not found with id " + id));
    }

    public DishDto add(DishDto dish) {
        if (dish.getRecipe() != null && dish.getRecipe().getId() != null) {
            recipeService.add(dish.getRecipe());
        }
        return conversionService.convert(
            dishRepository.save(Objects.requireNonNull(conversionService.convert(dish, Dish.class))), DishDto.class
        );
    }

    public DishDto update(Long id, DishDto chefDetails) {
        DishDto dish = getById(id);
        dish.setName(chefDetails.getName());
        dish.setRecipe(chefDetails.getRecipe());
        dishRepository.save(Objects.requireNonNull(conversionService.convert(dish, Dish.class)));
        return dish;
    }

    public Map<String, Boolean> delete(Long id) {
        DishDto dish = getById(id);
        dishRepository.delete(Objects.requireNonNull(conversionService.convert(dish, Dish.class)));
        Map<String, Boolean> dishMap = new HashMap<>();
        dishMap.put("Dish with id " + id + " is deleted ", Boolean.TRUE);
        return dishMap;
    }

}
