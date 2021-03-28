package com.practice.cooking.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.practice.cooking.converter.ChefConverter;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Chef;
import com.practice.cooking.service.ChefService;
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
@RequestMapping("/api/chefs")
public class ChefController {

    @Autowired
    private ChefService chefService;

    @Autowired
    ChefConverter chefConverter;

    @GetMapping("/")
    public List<ChefDto> getAllChefs() {
        return chefService.getAll()
            .stream()
            .map(chef -> toDTO(chef))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ChefDto getChefById(@PathVariable(value = "id") Long id) throws NotFoundException {
        return toDTO(chefService.getById(id));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createChef(@RequestBody ChefDto chef) {
        chefService.add(toEntity(chef));
    }

    @PutMapping("/{id}")
    public void updateChef(@PathVariable(value = "id") Long id, @RequestBody ChefDto chefDetails) {
        chefService.update(id, toEntity(chefDetails));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteChef(@PathVariable(value = "id") Long id) {
        chefService.delete(id);
    }

    public Chef toEntity(ChefDto dto) {
        Chef entity = chefConverter.convertToEntity(dto);
        return entity;
    }

    public ChefDto toDTO(Chef entity) {
        ChefDto dto = chefConverter.convert(entity);
        return dto;
    }
}
