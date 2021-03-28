package com.practice.cooking.service;

import java.util.List;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.repository.IngredientRepository;
import com.practice.cooking.utils.DatabaseSequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    private IngredientRepository ingredientRepository;

    private MongoOperations mongoOperations;

    @Autowired
    private DatabaseSequenceGenerator sequenceGenerator;
    
    @Autowired
    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Autowired
    public void setIngredientRepository(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient getById(Long id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new NotFoundException("Ingredient not found with id " + id));
    }

    public Ingredient add(Ingredient ingredient) {
        ingredient.setId(sequenceGenerator.generateSequence(Ingredient.SEQUENCE_NAME));
        return ingredientRepository.save(ingredient);
    }

    public void update(Long id, Ingredient ingredientDetails) {
        Ingredient ingredient = getById(id);
        ingredient.setName(ingredientDetails.getName());
        ingredient.setUnit(ingredientDetails.getUnit());
        ingredient.setQuantity(ingredientDetails.getQuantity());
        ingredientRepository.save(ingredient);
    }
    
    public void delete(Long id) {
        Ingredient ingredient = getById(id);
        ingredientRepository.delete(ingredient);
    }
}
