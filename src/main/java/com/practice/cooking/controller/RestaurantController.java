package com.practice.cooking.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.service.RestaurantService;
import com.practice.cooking.validator.RestaurantDtoValidator;
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
@RequestMapping("/api/restaurants")
@Validated
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final RestaurantDtoValidator restaurantDtoValidator;

    @InitBinder
    public void bindValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(restaurantDtoValidator);
    }

    @GetMapping()
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<RestaurantDto> restaurantDtoList = restaurantService.getAll();
        return new ResponseEntity<>(restaurantDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable(value = "id") Long id) throws NotFoundException {
        return new ResponseEntity<>(restaurantService.getById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestaurantDto> createRestaurant(@Valid @RequestBody RestaurantDto restaurant) throws JsonProcessingException {
        RestaurantDto restaurantDto = restaurantService.add(restaurant);
        return new ResponseEntity<>(restaurantDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestaurantDto> updateRestaurant(@Valid @PathVariable(value = "id") Long id, @RequestBody RestaurantDto restaurantDetails) throws NotFoundException, JsonProcessingException {
        RestaurantDto restaurantDto =restaurantService.update(id,restaurantDetails);
        return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteRestaurant(@PathVariable(value = "id") Long id) throws NotFoundException, JsonProcessingException {
        return new ResponseEntity<>(restaurantService.delete(id), HttpStatus.ACCEPTED);
    }

}
