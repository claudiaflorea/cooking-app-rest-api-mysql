package com.practice.cooking.repository;

import com.practice.cooking.model.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, Long> {
}
