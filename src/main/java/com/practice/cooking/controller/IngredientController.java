package com.practice.cooking.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ingredients")
@Validated
public class IngredientController {

    private final IngredientService ingredientService;

    private final ConversionService conversionService;

    @GetMapping()
    public ResponseEntity<List<IngredientDto>> findAllIngredients() {
        List<IngredientDto> ingredientDtos = ingredientService.getAll().stream()
            .map(ingredient -> conversionService.convert(ingredient, IngredientDto.class))
            .collect(Collectors.toList());
        return new ResponseEntity<>(ingredientDtos, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<IngredientDto> findIngredientById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(conversionService.convert(ingredientService.getById(id), IngredientDto.class), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<IngredientDto> createIngredient(@Valid @RequestBody IngredientDto ingredient) {
        IngredientDto ingredientDto = conversionService.convert(ingredientService.add(conversionService.convert(ingredient, Ingredient.class)), IngredientDto.class);
        return new ResponseEntity<>(ingredientDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<IngredientDto> updateIngredient(@Valid @PathVariable(value = "id") Long id, @RequestBody IngredientDto ingredientDetails) throws NotFoundException {
        IngredientDto ingredientDto = conversionService.convert(ingredientService.update(id, conversionService.convert(ingredientDetails, Ingredient.class)), IngredientDto.class);
        return new ResponseEntity<>(ingredientDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteIngredient(@PathVariable(value = "id") Long id) throws NotFoundException {
        return new ResponseEntity<>(ingredientService.delete(id), HttpStatus.ACCEPTED);
    }

}
