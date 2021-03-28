package com.practice.cooking.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.practice.cooking.converter.IngredientConverter;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/ingredients")
public class IngredientController {
    
    @Autowired
    private IngredientService ingredientService;

    @Autowired
    IngredientConverter ingredientConverter;
    
    @GetMapping("/")
    public List<IngredientDto> findAllIngredients() {
        return ingredientService.getAll()
            .stream()
            .map(ingredient -> toDTO(ingredient))
            .collect(Collectors.toList());
    }
    
    @GetMapping("{id}")
    public IngredientDto findIngredientById(@PathVariable(value = "id") Long id) throws NotFoundException {
        return toDTO(ingredientService.getById(id));
    }
    
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createIngredient(@RequestBody IngredientDto ingredient) {
        ingredientService.add(toEntity(ingredient));
    }
    
    @PutMapping("/{id}")
    public void updateIngredient(@PathVariable(value = "id") Long id, @RequestBody IngredientDto ingredientDetails) throws NotFoundException {
        ingredientService.update(id, toEntity(ingredientDetails));
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteIngredient(@PathVariable(value = "id") Long id) throws NotFoundException {
        ingredientService.delete(id);
    }

    public Ingredient toEntity(IngredientDto dto) {
        Ingredient entity = ingredientConverter.convertToEntity(dto);
        return entity;
    }

    public IngredientDto toDTO(Ingredient entity) {
        IngredientDto dto = ingredientConverter.convert(entity);
        return dto;
    }
}
