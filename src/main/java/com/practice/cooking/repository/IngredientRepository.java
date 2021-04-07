package com.practice.cooking.repository;

import java.util.List;

import com.practice.cooking.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    
    List<Ingredient> findAllByName(String name);
    
    @Query(value = "SELECT * FROM ingredients WHERE i_quantity > 1 and i_unit = 0 ", nativeQuery = true)
    List<Ingredient> findAllHeavierThan1Kg();
    
}
