package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.repository.IngredientRepository;
import com.practice.cooking.utils.DatabaseSequenceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    private final MongoOperations mongoOperations;

    private final DatabaseSequenceGenerator sequenceGenerator;
    
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

    public Ingredient update(Long id, Ingredient ingredientDetails) {
        Ingredient ingredient = getById(id);
        ingredient.setName(ingredientDetails.getName());
        ingredient.setUnit(ingredientDetails.getUnit());
        ingredient.setQuantity(ingredientDetails.getQuantity());
        ingredientRepository.save(ingredient);
        return ingredient;
    }
    
    public Map<String, Boolean> delete(Long id) {
        Ingredient ingredient = getById(id);
        ingredientRepository.delete(ingredient);
        Map<String, Boolean> ingredientMap = new HashMap<>();
        ingredientMap.put("Ingredient with id " + id + " is deleted ", Boolean.TRUE);
        return ingredientMap;
    }
}
