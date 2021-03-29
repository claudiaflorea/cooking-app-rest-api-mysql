package com.practice.cooking.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.practice.cooking.converter.IngredientEntityToDtoConverter;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    private final ConversionService conversionService;

    @GetMapping()
    public List<IngredientDto> findAllIngredients() {
        return ingredientService.getAll()
            .stream()
            .map(ingredient -> conversionService.convert(ingredient, IngredientDto.class))
            .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public IngredientDto findIngredientById(@PathVariable(value = "id") Long id) throws NotFoundException {
        return conversionService.convert(ingredientService.getById(id), IngredientDto.class);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createIngredient(@RequestBody IngredientDto ingredient) {
        ingredientService.add(conversionService.convert(ingredient, Ingredient.class));
    }

    @PutMapping("/{id}")
    public void updateIngredient(@PathVariable(value = "id") Long id, @RequestBody IngredientDto ingredientDetails) throws NotFoundException {
        ingredientService.update(id, conversionService.convert(ingredientDetails, Ingredient.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteIngredient(@PathVariable(value = "id") Long id) throws NotFoundException {
        ingredientService.delete(id);
    }

}
