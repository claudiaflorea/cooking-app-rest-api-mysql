package com.practice.cooking.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.jms.Queue;
import javax.jms.Topic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.mapper.RecipeDtoToEntityMapper;
import com.practice.cooking.mapper.RecipeEntityToDtoMapper;
import com.practice.cooking.publisher.Publisher;
import com.practice.cooking.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private static final Queue RECIPE_QUEUE     = new ActiveMQQueue("recipe_queue");
    private static final Topic RECIPE_TOPIC     = new ActiveMQTopic("recipe_topic");

    private final RecipeRepository recipeRepository;

    private final IngredientService ingredientService;

    private final RecipeDtoToEntityMapper dtoToEntityMapper;

    private final RecipeEntityToDtoMapper entityToDtoMapper;

    private final Publisher publisher;

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

    public List<RecipeDto> getAllByName(String name) {
        return recipeRepository.findAllByName(name).stream()
            .map(recipe -> entityToDtoMapper.entityToDto(recipe))
            .collect(Collectors.toList());
    }

    public RecipeDto add(RecipeDto recipe) throws JsonProcessingException {
        if (recipe.getIngredients() != null) {
            for (IngredientDto ingredient : recipe.getIngredients()) {
                if (ingredient != null && ingredient.getId() != null) {
                    ingredientService.add(ingredient);
                }
            }
        }
        publisher.sendToConsumerWhenAddingNewRecord(RECIPE_QUEUE, recipe);
        publisher.notifyAllSubscribersWhenAddingRecord(RECIPE_TOPIC, recipe);
        return entityToDtoMapper.entityToDto(
            recipeRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(recipe)))
        );
    }

    public RecipeDto update(Long id, RecipeDto recipeDetails) throws JsonProcessingException {
        RecipeDto recipe = getById(id);
        recipe.setName(recipeDetails.getName());
        recipe.setCookingTime(recipeDetails.getCookingTime());
        recipe.setDifficulty(recipeDetails.getDifficulty());
        recipe.setRecipeType(recipeDetails.getRecipeType());
        recipe.setIngredients(recipeDetails.getIngredients());
        recipeRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(recipe)));
        publisher.sendToConsumerWhenUpdatingRecord(RECIPE_QUEUE, recipe);
        return recipe;
    }

    public Map<String, Boolean> delete(Long id) throws JsonProcessingException {
        RecipeDto recipe = getById(id);
        recipeRepository.delete(dtoToEntityMapper.dtoToEntity(recipe));
        publisher.sendToConsumerWhenDeletingRecord(RECIPE_QUEUE, recipe);
        Map<String, Boolean> recipeMap = new HashMap<>();
        recipeMap.put("Recipe with id " + id + " is deleted ", Boolean.TRUE);
        return recipeMap;
    }

    @Transactional(rollbackFor = {SQLException.class})
    public RecipeDto createSmoothieRecipe(RecipeDto recipe) throws JsonProcessingException {
        if (recipe.getIngredients() != null) {
            for (IngredientDto ingredient : recipe.getIngredients()) {
                if (ingredient != null) {
                    ingredientService.add(ingredient);
                }
            }
        }
        publisher.sendToConsumerWhenAddingNewRecord(RECIPE_QUEUE, recipe);
        return entityToDtoMapper.entityToDto(
            recipeRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(recipe)))
        );
    }

}
