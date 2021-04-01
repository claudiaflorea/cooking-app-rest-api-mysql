package com.practice.cooking.scheduler;

import java.util.Comparator;
import java.util.NoSuchElementException;

import com.practice.cooking.model.Restaurant;
import com.practice.cooking.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class RestaurantJobScheduler {

    private final RestaurantService restaurantService;

    @Scheduled(fixedDelay = 120000, initialDelay = 20000)
    public void displayRestaurants() {
        System.out.println("Displaying all restaurants in DB every 2 minutes .......");
        restaurantService.getAll().stream()
            .forEach(restaurant -> System.out.println(restaurant));
        System.out.println("Total number of restaurants: " + restaurantService.getAll().stream().count());
    }
    
    @Scheduled(fixedDelayString = "120000", initialDelayString = "160000")
    public void displayLastAddedRestaurant() {
        Restaurant restaurant = restaurantService.getAll().stream()
            .max(Comparator.comparing(Restaurant::getId))
            .orElseThrow(NoSuchElementException::new);
        System.out.println("Last added restaurant in the DB is: " + restaurant.getId() + " - " + restaurant.getName());
    }
    
    // yearly upgrade restaurants level with 1 star
    @Scheduled(cron = "0 0 0 1 1 ?")
    public void upgradingRestaurant() {
        restaurantService.getAll().stream()
            .filter(restaurant -> restaurant.getStars() < 5)
            .forEach(updatedRestaurant -> {
                updatedRestaurant.setStars(updatedRestaurant.getStars() + 1);
                restaurantService.update(updatedRestaurant.getId(), updatedRestaurant);
            });
    }
}
