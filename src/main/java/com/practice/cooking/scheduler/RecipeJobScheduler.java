package com.practice.cooking.scheduler;

import java.util.Comparator;
import java.util.NoSuchElementException;

import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class RecipeJobScheduler {

    private final RecipeService recipeService;

    @Scheduled(fixedDelay = 120000, initialDelay = 20000)
    public void displayRecipes() {
        System.out.println("Displaying all recipes in DB every 2 minutes .......");
        recipeService.getAll().stream()
            .forEach(recipe -> System.out.println(recipe));
        System.out.println("Total number of recipes: " + recipeService.getAll().stream().count());
    }

    @Scheduled(fixedDelayString = "120000", initialDelayString = "150000")
    public void displayLastAddedRecipe() {
        Recipe recipe = recipeService.getAll().stream()
            .max(Comparator.comparing(Recipe::getId))
            .orElseThrow(NoSuchElementException::new);
        System.out.println("Last added recipe in the DB is: " + recipe.getId() + " - " + recipe.getName());
    }
    
    @Scheduled(cron = "* */10 * * * *")
    public void changeRecipeDifficultyLevelToMediumAccordingToTheCookingTime() {
        recipeService.getAll().stream()
            .filter(recipe -> recipe.getCookingTime() > 1 && recipe.getCookingTime() <= 3)
            .forEach(updatedRecipe -> {
                updatedRecipe.setDifficulty(Difficulty.MEDIUM);
                recipeService.update(updatedRecipe.getId(), updatedRecipe);
            });
    }

    @Scheduled(cron = "* */10 * * * *")
    public void changeRecipeDifficultyLevelToHardAccordingToTheCookingTime() {
        recipeService.getAll().stream()
            .filter(recipe -> recipe.getCookingTime() > 3)
            .forEach(updatedRecipe -> {
                updatedRecipe.setDifficulty(Difficulty.HARD);
                recipeService.update(updatedRecipe.getId(), updatedRecipe);
            });
    }
}
