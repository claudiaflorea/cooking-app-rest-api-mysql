package com.practice.cooking.service;

import java.util.List;

import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.repository.DishRepository;
import com.practice.cooking.repository.RecipeRepository;
import com.practice.cooking.utils.DatabaseSequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class DishService {

    private DishRepository dishRepository;

    private MongoOperations mongoOperations;

    @Autowired
    private DatabaseSequenceGenerator sequenceGenerator;

    @Autowired
    private RecipeService recipeService;
    
    @Autowired
    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Autowired
    public void setDishRepository(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    public Dish getById(Long id) {
        return dishRepository.findById(id).orElseThrow(() -> new NotFoundException("Dish not found with id " + id));
    }

    public Dish add(Dish dish) {
        dish.setId(sequenceGenerator.generateSequence(Dish.SEQUENCE_NAME));
        if (dish.getRecipe() != null) {
            recipeService.add(dish.getRecipe());
        }
        return dishRepository.save(dish);
    }

    public void update(Long id, Dish chefDetails) {
        Dish dish = getById(id);
        dish.setName(chefDetails.getName());
        dish.setRecipe(chefDetails.getRecipe());
        dishRepository.save(dish);
    }

    public void delete(Long id) {
        Dish dish = getById(id);
        dishRepository.delete(dish);
    }
}
