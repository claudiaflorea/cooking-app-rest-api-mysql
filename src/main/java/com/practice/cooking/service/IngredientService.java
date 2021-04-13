package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.jms.Queue;
import javax.jms.Topic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.mapper.IngredientDtoToEntityMapper;
import com.practice.cooking.mapper.IngredientEntityToDtoMapper;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.publisher.Publisher;
import com.practice.cooking.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private static final Queue INGREDIENT_QUEUE = new ActiveMQQueue("ingredient_queue");
    private static final Topic INGREDIENT_TOPIC = new ActiveMQTopic("ingredient_topic");

    private final IngredientRepository ingredientRepository;

    private final IngredientDtoToEntityMapper dtoToEntityMapper;

    private final IngredientEntityToDtoMapper entityToDtoMapper;

    private final Publisher publisher;

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

    public List<IngredientDto> getAllByName(String name) {
        return ingredientRepository.findAllByName(name).stream()
            .map(ingredient -> entityToDtoMapper.entityToDto(ingredient))
            .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public IngredientDto add(IngredientDto ingredient) throws JsonProcessingException {
        publisher.sendToConsumerWhenAddingNewRecord(INGREDIENT_QUEUE, ingredient);
        publisher.notifyAllSubscribersWhenAddingRecord(INGREDIENT_TOPIC, ingredient);
        return entityToDtoMapper.entityToDto(
            ingredientRepository.save(dtoToEntityMapper.dtoToEntity(ingredient))
        );
    }

    public IngredientDto update(Long id, IngredientDto ingredientDetails) throws JsonProcessingException {
        IngredientDto ingredient = getById(id);
        ingredient.setName(ingredientDetails.getName());
        ingredient.setUnit(ingredientDetails.getUnit());
        ingredient.setQuantity(ingredientDetails.getQuantity());
        ingredientRepository.save(dtoToEntityMapper.dtoToEntity(ingredient));
        publisher.sendToConsumerWhenUpdatingRecord(INGREDIENT_QUEUE, ingredient);
        return ingredient;
    }

    public Map<String, Boolean> delete(Long id) throws JsonProcessingException {
        IngredientDto ingredient = getById(id);
        ingredientRepository.delete(dtoToEntityMapper.dtoToEntity(ingredient));
        publisher.sendToConsumerWhenDeletingRecord(INGREDIENT_QUEUE, ingredient);
        Map<String, Boolean> ingredientMap = new HashMap<>();
        ingredientMap.put("Ingredient with id " + id + " is deleted ", Boolean.TRUE);
        return ingredientMap;
    }

    public void deleteAll() throws JsonProcessingException {
        ingredientRepository.deleteAll();
        publisher.sendToConsumerWhenDeletingRecord(INGREDIENT_QUEUE, "Deleted all ingredients");
    }

}
