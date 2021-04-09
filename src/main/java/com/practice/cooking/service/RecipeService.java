package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.mapper.RecipeDtoToEntityMapper;
import com.practice.cooking.mapper.RecipeEntityToDtoMapper;
import com.practice.cooking.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final IngredientService ingredientService;
    
    private final RecipeDtoToEntityMapper dtoToEntityMapper;

    private final RecipeEntityToDtoMapper entityToDtoMapper;


    public List<RecipeDto> getAll() {
        return recipeRepository.findAll().stream()
            .map(recipe -> entityToDtoMapper.entityToDto(recipe))
            .collect(Collectors.toList());
    }

    public RecipeDto getById(Long id) {
        return recipeRepository.findById(id)
            .map(recipe -> entityToDtoMapper.entityToDto(recipe))
            .orElseThrow(() -> new NotFoundException("Recipe not found with id " + id));
    }

    public RecipeDto add(RecipeDto recipe) {
        if (recipe.getIngredients() != null) {
            for (IngredientDto ingredient : recipe.getIngredients()) {
                if (ingredient != null && ingredient.getId() != null) {
                    ingredientService.add(ingredient);
                }
            }
        }
        return entityToDtoMapper.entityToDto(
            recipeRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(recipe)))
        );
    }

    public RecipeDto update(Long id, RecipeDto recipeDetails) {
        RecipeDto recipe = getById(id);
        recipe.setName(recipeDetails.getName());
        recipe.setCookingTime(recipeDetails.getCookingTime());
        recipe.setDifficulty(recipeDetails.getDifficulty());
        recipe.setRecipeType(recipeDetails.getRecipeType());
        recipe.setIngredients(recipeDetails.getIngredients());
        recipeRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(recipe)));
        return recipe;
    }

    public Map<String, Boolean> delete(Long id) {
        RecipeDto recipe = getById(id);
        recipeRepository.delete(dtoToEntityMapper.dtoToEntity(recipe));
        Map<String, Boolean> recipeMap = new HashMap<>();
        recipeMap.put("Recipe with id " + id + " is deleted ", Boolean.TRUE);
        return recipeMap;
    }

}
