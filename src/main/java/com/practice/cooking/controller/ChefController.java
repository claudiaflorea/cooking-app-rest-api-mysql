package com.practice.cooking.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Chef;
import com.practice.cooking.service.ChefService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/chefs")
public class ChefController {

    private final ChefService chefService;

    /// inject through interface
    // private final ChefConverter chefConverter;
    private final ConversionService conversionService;

    @GetMapping
    public List<ChefDto> getAllChefs() {
        return chefService.getAll()
            .stream()
            .map(chef -> conversionService.convert(chef, ChefDto.class))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    // use ResponseEntity (research)
    public ChefDto getChefById(@PathVariable(value = "id") Long id) throws NotFoundException {
        return conversionService.convert(chefService.getById(id), ChefDto.class);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void createChef(@Valid ChefDto chef) {
        chefService.add(conversionService.convert(chef, Chef.class));
    }

    @PutMapping("/{id}")
    // add a specific converter from dto to entity
    public void updateChef(@PathVariable(value = "id") Long id, @RequestBody ChefDto chefDetails) {
        chefService.update(id, conversionService.convert(chefDetails, Chef.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteChef(@PathVariable(value = "id") Long id) {
        chefService.delete(id);
    }
    
}
