package com.practice.cooking.scheduler;

import java.util.Comparator;
import java.util.NoSuchElementException;

import com.practice.cooking.model.Dish;
import com.practice.cooking.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class DishJobScheduler {

    private final DishService dishService;

    @Scheduled(fixedDelay = 120000, initialDelay = 20000)
    public void displayDishes() {
        System.out.println("Displaying all dishes in DB every 2 minutes .......");
        dishService.getAll().stream()
            .forEach(dish -> System.out.println(dish));
        System.out.println("Total number of dishes: " + dishService.getAll().stream().count());
    }
    
    @Scheduled(fixedDelayString = "120000", initialDelayString = "130000")
    public void displayLastAddedDish() {
        Dish dish = dishService.getAll().stream()
            .max(Comparator.comparing(Dish::getId))
            .orElseThrow(NoSuchElementException::new);
        System.out.println("Last added dish in the DB is: " + dish.getId() + " - " + dish.getName());
    }
    
    // at the begining of every hour of the day, updates dishes name in case they are not compliant to their recipe names (if they have a recipe)
    @Scheduled(cron = "0 0 * * * *")
    public void updateDishNameAccordingly() {
        dishService.getAll().stream()
            .filter(dish -> dish.getName() != dish.getRecipe().getName())
            .forEach(updatedDish -> {
                updatedDish.setName(updatedDish.getRecipe() == null ? updatedDish.getRecipe().getName() : updatedDish.getName());
                dishService.update(updatedDish.getId(), updatedDish);
            });
    }
}
