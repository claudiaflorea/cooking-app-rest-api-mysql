package com.practice.cooking.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.practice.cooking.converter.DishConverter;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Dish;
import com.practice.cooking.service.DishService;
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
@RequestMapping("/api/dishes")
public class DishController {
    
    @Autowired
    private DishService dishService;

    @Autowired
    DishConverter dishConverter;
     
    @GetMapping("/")
    public List<DishDto> getAllDishes() {
        return dishService.getAll()
            .stream()
            .map(dish -> toDTO(dish))
            .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public DishDto getDishById(@PathVariable(value="id") Long id) {
        return toDTO(dishService.getById(id));
    }
    
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createDish(@RequestBody DishDto dish) {
        dishService.add(toEntity(dish));
    }
    
    @PutMapping("/{id}")
    public void updateDish(@PathVariable(value = "id") Long id, @RequestBody DishDto dishDetails) throws NotFoundException {
        dishService.update(id, toEntity(dishDetails));
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteDish(@PathVariable(value = "id") Long id) throws NotFoundException {
        dishService.delete(id);
    }

    public Dish toEntity(DishDto dto) {
        Dish entity = dishConverter.convertToEntity(dto);
        return entity;
    }

    public DishDto toDTO(Dish entity) {
        DishDto dto = dishConverter.convert(entity);
        return dto;
    }
}
