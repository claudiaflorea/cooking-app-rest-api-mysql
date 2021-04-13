package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.jms.Queue;
import javax.jms.Topic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.mapper.DishDtoToEntityMapper;
import com.practice.cooking.mapper.DishEntityToDtoMapper;
import com.practice.cooking.publisher.Publisher;
import com.practice.cooking.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishService {

    private static final Queue DISH_QUEUE = new ActiveMQQueue("dish_queue");
    private static final Topic DISH_TOPIC = new ActiveMQTopic("dish_topic");
    private static final Topic RECIPE_TOPIC = new ActiveMQTopic("recipe_topic");

    private final DishRepository dishRepository;

    private final RecipeService recipeService;

    private final DishDtoToEntityMapper dtoToEntityMapper;

    private final DishEntityToDtoMapper entityToDtoMapper;

    private final Publisher publisher;
    
    public List<DishDto> getAll() {
        return dishRepository.findAll().stream()
            .map(dish -> entityToDtoMapper.entityToDto(dish))
            .collect(Collectors.toList());
    }

    public DishDto getById(Long id) {
        return dishRepository.findById(id)
            .map(dish -> entityToDtoMapper.entityToDto(dish))
            .orElseThrow(() -> new NotFoundException("Dish not found with id " + id));
    }

    public List<DishDto> getAllByName(String name) {
        return dishRepository.findAllByName(name).stream()
            .map(dish -> entityToDtoMapper.entityToDto(dish))
            .collect(Collectors.toList());
    }

    public DishDto add(DishDto dish) throws JsonProcessingException {
        if (dish.getRecipe() != null && dish.getRecipe().getId() != null) {
            recipeService.add(dish.getRecipe());
            publisher.notifyAllSubscribersWhenAddingRecord(RECIPE_TOPIC, dish.getRecipe());
        }
        publisher.sendToConsumerWhenAddingNewRecord(DISH_QUEUE, dish);
        publisher.notifyAllSubscribersWhenAddingRecord(DISH_TOPIC, dish);
        return entityToDtoMapper.entityToDto(
            dishRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(dish)))
        );
    }

    public DishDto update(Long id, DishDto chefDetails) throws JsonProcessingException {
        DishDto dish = getById(id);
        dish.setName(chefDetails.getName());
        dish.setRecipe(chefDetails.getRecipe());
        dishRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(dish)));
        publisher.sendToConsumerWhenUpdatingRecord(DISH_QUEUE, dish);
        return dish;
    }

    public Map<String, Boolean> delete(Long id) throws JsonProcessingException {
        DishDto dish = getById(id);
        dishRepository.delete(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(dish)));
        publisher.sendToConsumerWhenDeletingRecord(DISH_QUEUE, dish);
        Map<String, Boolean> dishMap = new HashMap<>();
        dishMap.put("Dish with id " + id + " is deleted ", Boolean.TRUE);
        return dishMap;
    }

}
