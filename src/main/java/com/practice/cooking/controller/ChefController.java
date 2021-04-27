package com.practice.cooking.controller;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.service.ChefService;
import com.practice.cooking.validator.ChefDtoValidator;
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
@RequestMapping("/api/chefs")
@Validated
public class ChefController {

    private final ChefService chefService;

    private final ChefDtoValidator chefDtoValidator;
    
    @InitBinder
    public void bindValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(chefDtoValidator);
    }
    
    @GetMapping
    public ResponseEntity<List<ChefDto>> getAllChefs() {
        List<ChefDto> chefDtoList = chefService.getAll();
        return new ResponseEntity<>(chefDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChefDto> getChefById(@PathVariable(value = "id") Long id) throws NotFoundException {
        return new ResponseEntity<>(chefService.getById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ChefDto> createChef(@Valid @RequestBody ChefDto chefDto) throws JsonProcessingException {
        ChefDto chef = chefService.add(chefDto);
        return new ResponseEntity<>(chef, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ChefDto> updateChef(@Valid @PathVariable(value = "id") Long id, @RequestBody ChefDto chefDetails) throws JsonProcessingException {
        ChefDto chefDto = chefService.update(id, chefDetails);
        return new ResponseEntity<>(chefDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteChef(@PathVariable(value = "id") Long id) throws JsonProcessingException {
        return new ResponseEntity<>(chefService.delete(id), HttpStatus.ACCEPTED);
    }

}
