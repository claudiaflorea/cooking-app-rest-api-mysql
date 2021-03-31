package com.practice.cooking.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.practice.cooking.dto.DishDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Dish;
import com.practice.cooking.service.DishService;
import com.practice.cooking.validator.DishDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
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
@RequestMapping("/api/dishes")
@Validated
public class DishController {

    private final DishService dishService;

    private final ConversionService conversionService;
    
    @InitBinder
    private void bindValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new DishDtoValidator()); 
    }

    @GetMapping()
    public ResponseEntity<List<DishDto>> getAllDishes() {
        List<DishDto> dishDtoList = dishService.getAll().stream()
            .map(dish -> conversionService.convert(dish, DishDto.class))
            .collect(Collectors.toList());
        return new ResponseEntity<>(dishDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDto> getDishById(@PathVariable(value = "id") Long id) {
        DishDto dishDto = conversionService.convert(dishService.getById(id), DishDto.class);
        return new ResponseEntity<>(dishDto, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DishDto> createDish(@Valid @RequestBody DishDto dish) {
        DishDto dishDto = conversionService.convert(dishService.add(conversionService.convert(dish, Dish.class)), DishDto.class);
        return new ResponseEntity<>(dishDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DishDto> updateDish(@Valid @PathVariable(value = "id") Long id, @RequestBody DishDto dishDetails) throws NotFoundException {
        DishDto dishDto = conversionService.convert(dishService.update(id, conversionService.convert(dishDetails, Dish.class)), DishDto.class);
        return new ResponseEntity<>(dishDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteDish(@PathVariable(value = "id") Long id) throws NotFoundException {
        return new ResponseEntity<>(dishService.delete(id), HttpStatus.ACCEPTED);
    }
}
