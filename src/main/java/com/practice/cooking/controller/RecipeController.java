package com.practice.cooking.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.service.RecipeService;
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
@RequestMapping("/api/recipes")
@Validated
public class RecipeController {

    private final RecipeService recipeService;

    private final ConversionService conversionService;

    @GetMapping()
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipeDtoList = recipeService.getAll().stream()
            .map(recipe -> conversionService.convert(recipe, RecipeDto.class))
            .collect(Collectors.toList());
        return new ResponseEntity<>(recipeDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable(value = "id") Long id) throws NotFoundException {
        return new ResponseEntity<>(conversionService.convert(recipeService.getById(id), RecipeDto.class), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RecipeDto> createRecipe(@Valid @RequestBody RecipeDto recipe) {
        RecipeDto recipeDto = conversionService.convert(recipeService.add(conversionService.convert(recipe, Recipe.class)), RecipeDto.class);
        return new ResponseEntity<>(recipeDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RecipeDto> updateRecipe(@Valid @PathVariable(value = "id") Long id, @RequestBody RecipeDto recipeDetails) throws NotFoundException {
        RecipeDto recipeDto =  conversionService.convert(recipeService.update(id, conversionService.convert(recipeDetails, Recipe.class)), RecipeDto.class);
        return new ResponseEntity<>(recipeDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteRecipe(@PathVariable(value = "id") Long id) throws NotFoundException {
        return new ResponseEntity<>(recipeService.delete(id), HttpStatus.ACCEPTED);
    }
    
}
