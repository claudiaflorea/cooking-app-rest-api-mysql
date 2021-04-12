package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.mapper.IngredientDtoToEntityMapper;
import com.practice.cooking.mapper.IngredientEntityToDtoMapper;
import com.practice.cooking.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    private final IngredientDtoToEntityMapper dtoToEntityMapper;

    private final IngredientEntityToDtoMapper entityToDtoMapper;

    public List<IngredientDto> getAll() {
        return ingredientRepository.findAll().stream().
            map(ingredient -> entityToDtoMapper.entityToDto(ingredient))
            .collect(Collectors.toList());
    }

    public IngredientDto getById(Long id) {
        return ingredientRepository.findById(id)
            .map(ingredient -> entityToDtoMapper.entityToDto(ingredient))
            .orElseThrow(() -> new NotFoundException("Ingredient not found with id " + id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public IngredientDto add(IngredientDto ingredient) {
        return entityToDtoMapper.entityToDto(
            ingredientRepository.save(dtoToEntityMapper.dtoToEntity(ingredient))
        );
    }

    public IngredientDto update(Long id, IngredientDto ingredientDetails) {
        IngredientDto ingredient = getById(id);
        ingredient.setName(ingredientDetails.getName());
        ingredient.setUnit(ingredientDetails.getUnit());
        ingredient.setQuantity(ingredientDetails.getQuantity());
        ingredientRepository.save(dtoToEntityMapper.dtoToEntity(ingredient));
        return ingredient;
    }

    public Map<String, Boolean> delete(Long id) {
        IngredientDto ingredient = getById(id);
        ingredientRepository.delete(dtoToEntityMapper.dtoToEntity(ingredient));
        Map<String, Boolean> ingredientMap = new HashMap<>();
        ingredientMap.put("Ingredient with id " + id + " is deleted ", Boolean.TRUE);
        return ingredientMap;
    }

}
