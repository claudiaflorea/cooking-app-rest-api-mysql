package com.practice.cooking.controller;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.service.IngredientService;
import com.practice.cooking.validator.IngredientDtoValidator;
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
@RequestMapping("api/ingredients")
@Validated
public class IngredientController {

    private final IngredientService ingredientService;

    private final IngredientDtoValidator ingredientDtoValidator;

    @InitBinder
    public void bindValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(ingredientDtoValidator);
    }

    @GetMapping()
    public ResponseEntity<List<IngredientDto>> findAllIngredients() {
        List<IngredientDto> ingredientDtos = ingredientService.getAll();
        return new ResponseEntity<>(ingredientDtos, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<IngredientDto> findIngredientById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(ingredientService.getById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<IngredientDto> createIngredient(@Valid @RequestBody IngredientDto ingredient) throws JsonProcessingException {
        IngredientDto ingredientDto = ingredientService.add(ingredient);
        return new ResponseEntity<>(ingredientDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<IngredientDto> updateIngredient(@Valid @PathVariable(value = "id") Long id, @RequestBody IngredientDto ingredientDetails) throws NotFoundException, JsonProcessingException {
        IngredientDto ingredientDto = ingredientService.update(id,ingredientDetails);
        return new ResponseEntity<>(ingredientDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteIngredient(@PathVariable(value = "id") Long id) throws NotFoundException, JsonProcessingException {
        return new ResponseEntity<>(ingredientService.delete(id), HttpStatus.ACCEPTED);
    }

}
