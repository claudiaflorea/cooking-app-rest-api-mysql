package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    private final ConversionService conversionService;

    public List<IngredientDto> getAll() {
        return ingredientRepository.findAll().stream().
            map(ingredient -> conversionService.convert(ingredient, IngredientDto.class))
            .collect(Collectors.toList());
    }

    public IngredientDto getById(Long id) {
        return ingredientRepository.findById(id)
            .map(ingredient -> conversionService.convert(ingredient, IngredientDto.class))
            .orElseThrow(() -> new NotFoundException("Ingredient not found with id " + id));
    }

    public IngredientDto add(IngredientDto ingredient) {
        return conversionService.convert(
            ingredientRepository.save(conversionService.convert(ingredient, Ingredient.class)), IngredientDto.class
        );
    }

    public IngredientDto update(Long id, IngredientDto ingredientDetails) {
        IngredientDto ingredient = getById(id);
        ingredient.setName(ingredientDetails.getName());
        ingredient.setUnit(ingredientDetails.getUnit());
        ingredient.setQuantity(ingredientDetails.getQuantity());
        ingredientRepository.save(conversionService.convert(ingredient, Ingredient.class));
        return ingredient;
    }
    
    public Map<String, Boolean> delete(Long id) {
        IngredientDto ingredient = getById(id);
        ingredientRepository.delete(conversionService.convert(ingredient, Ingredient.class));
        Map<String, Boolean> ingredientMap = new HashMap<>();
        ingredientMap.put("Ingredient with id " + id + " is deleted ", Boolean.TRUE);
        return ingredientMap;
    }
 
}
