package com.practice.cooking.repository;

import java.util.List;

import com.practice.cooking.model.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, Long> {
    
    List<Ingredient> findAllByName(String name);
    
    @Query("{quantity: {$gte : 1}, unit: 'KG'}")
    List<Ingredient> findAllByHeavierThan1Kg();
}
