package com.practice.cooking.scheduler;

import java.util.Comparator;
import java.util.NoSuchElementException;

import com.practice.cooking.model.Ingredient;
import com.practice.cooking.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class IngredientJobScheduler {

    private final IngredientService ingredientService;

    @Scheduled(fixedDelay = 120000, initialDelay = 20000)
    public void displayIngredients() {
        System.out.println("Displaying all ingredients in DB every 2 minutes .......");
        ingredientService.getAll().stream()
            .forEach(ingredient -> System.out.println(ingredient));
        System.out.println("Total number of ingredients: " + ingredientService.getAll().stream().count());
    }
    
    @Scheduled(fixedDelayString = "120000", initialDelayString = "140000")
    public void displayLastAddedIngredient() {
        Ingredient ingredient = ingredientService.getAll().stream()
            .max(Comparator.comparing(Ingredient::getId))
            .orElseThrow(NoSuchElementException::new);
        System.out.println("Last added ingredient in the DB is: " + ingredient.getId() + " - " + ingredient.getName());
    }
}
