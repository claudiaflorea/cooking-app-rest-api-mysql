package com.practice.cooking.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.practice.cooking.converter.DishEntityToDtoConverter;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Dish;
import com.practice.cooking.service.DishService;
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
@RequestMapping("/api/dishes")
public class DishController {

    private final DishService dishService;

    private final ConversionService conversionService;

    @GetMapping()
    public List<DishDto> getAllDishes() {
        return dishService.getAll()
            .stream()
            .map(dish -> conversionService.convert(dish, DishDto.class))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DishDto getDishById(@PathVariable(value = "id") Long id) {
        return conversionService.convert(dishService.getById(id), DishDto.class);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createDish(@RequestBody DishDto dish) {
        dishService.add(conversionService.convert(dish, Dish.class));
    }

    @PutMapping("/{id}")
    public void updateDish(@PathVariable(value = "id") Long id, @RequestBody DishDto dishDetails) throws NotFoundException {
        dishService.update(id, conversionService.convert(dishDetails, Dish.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteDish(@PathVariable(value = "id") Long id) throws NotFoundException {
        dishService.delete(id);
    }
}
