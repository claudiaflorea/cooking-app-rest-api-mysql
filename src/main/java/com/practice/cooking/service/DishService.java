package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.repository.DishRepository;
import com.practice.cooking.repository.RecipeRepository;
import com.practice.cooking.utils.DatabaseSequenceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    private final MongoOperations mongoOperations;

    private final DatabaseSequenceGenerator sequenceGenerator;

    private final RecipeService recipeService;
    
    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    public Dish getById(Long id) {
        return dishRepository.findById(id).orElseThrow(() -> new NotFoundException("Dish not found with id " + id));
    }

    public Dish add(Dish dish) {
        dish.setId(sequenceGenerator.generateSequence(Dish.SEQUENCE_NAME));
        if (dish.getRecipe() != null && dish.getRecipe().getId() != null) {
            recipeService.add(dish.getRecipe());
        }
        return dishRepository.save(dish);
    }

    public Dish update(Long id, Dish chefDetails) {
        Dish dish = getById(id);
        dish.setName(chefDetails.getName());
        dish.setRecipe(chefDetails.getRecipe());
        dishRepository.save(dish);
        return dish;
    }

    public Map<String, Boolean> delete(Long id) {
        Dish dish = getById(id);
        dishRepository.delete(dish);
        Map<String, Boolean> dishMap = new HashMap<>();
        dishMap.put("Dish with id " + id + " is deleted ", Boolean.TRUE);
        return dishMap;
    }
    
    public List<Dish> getAllByName(String name) {
        return dishRepository.findAllByName(name);
    }
 
    public List<Dish> getDishesWithLiquidIngredients() {
        return dishRepository.findAllByRecipeContainingLiquidIngredients();
    }
    
}
