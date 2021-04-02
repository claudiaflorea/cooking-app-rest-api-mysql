package com.practice.cooking.scheduler;

import java.util.Comparator;
import java.util.NoSuchElementException;

import com.practice.cooking.model.Chef;
import com.practice.cooking.service.ChefService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
@Component
public class ChefJobScheduler {

    private final ChefService chefService;

    @Scheduled(fixedDelay = 120000, initialDelay = 20000)
    public void displayChefs() {
        System.out.println("Displaying all chefs in DB every 2 minutes after starting in 2 seconds after the app is started......");
        chefService.getAll().stream()
            .forEach(chef -> System.out.println(chef));
        System.out.println("Total number of chefs: " + chefService.getAll().stream().count());
    }
    
    @Scheduled(fixedDelayString = "120000", initialDelayString = "120000")
    public void displayLastAddedChef() {
        Chef chef = chefService.getAll().stream()
            .max(Comparator.comparing(Chef::getId))
            .orElseThrow(NoSuchElementException::new);
        System.out.println("Last added chef in the DB is: " + chef.getId() + " - " + chef.getName());
    }
    
    @Scheduled(cron = "* */10 * * * *")
    //every 10 minutes, check if there are any chefs in DB with naae set without "Chef " prefix and updates the records
    public void updateChefNames() {
        chefService.getAll().stream()
            .filter(chef -> !chef.getName().startsWith("Chef."))
            .forEach(updatedChef -> {
                updatedChef.setName("Chef. " + updatedChef.getName());
                chefService.update(updatedChef.getId(), updatedChef);
            });
    }
}
