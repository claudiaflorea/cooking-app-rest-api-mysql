package com.practice.cooking.controller;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.service.RecipeService;
import com.practice.cooking.validator.RecipeDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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

    private final RecipeDtoValidator recipeDtoValidator;

    @InitBinder
    public void bindValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(recipeDtoValidator);
    }

    @GetMapping()
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipeDtoList = recipeService.getAll();
        return new ResponseEntity<>(recipeDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable(value = "id") Long id) throws NotFoundException {
        return new ResponseEntity<>(recipeService.getById(id), HttpStatus.OK);
    }

//    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<RecipeDto> createRecipe(@Valid @RequestBody RecipeDto recipe) {
//        RecipeDto recipeDto = recipeService.add(recipe);
//        return new ResponseEntity<>(recipeDto, HttpStatus.CREATED);
//    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RecipeDto> createSmoothie(@Valid @RequestBody RecipeDto recipe) throws JsonProcessingException {
        RecipeDto recipeDto = recipeService.createSmoothieRecipe(recipe);
        return new ResponseEntity<>(recipeDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RecipeDto> updateRecipe(@Valid @PathVariable(value = "id") Long id, @RequestBody RecipeDto recipeDetails) throws NotFoundException, JsonProcessingException {
        RecipeDto recipeDto =  recipeService.update(id, recipeDetails);
        return new ResponseEntity<>(recipeDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteRecipe(@PathVariable(value = "id") Long id) throws NotFoundException, JsonProcessingException {
        return new ResponseEntity<>(recipeService.delete(id), HttpStatus.ACCEPTED);
    }
    
}
