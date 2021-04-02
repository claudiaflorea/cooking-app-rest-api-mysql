package com.practice.cooking.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.model.Unit;
import com.practice.cooking.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CookingApplicationIntegrationTests {

    @Test
    public void testRestaurant() {
        Restaurant restaurant = this.getRestaurant();
        assertEquals(restaurant.getId().toString(), "9");
        assertEquals(restaurant.getName(), "Mamma mia");
        assertEquals(restaurant.getStars().toString(), "5");
    }

    @Test
    public void testChef() {
        Chef chef = chefList().get(0);
        assertEquals(chef.getId().toString(), "10");
        assertEquals(chef.getName(), "Alberto");
    }

    @Test
    public void testDish() {
        Dish dish = dishesList().get(0);
        assertEquals(dish.getId().toString(), "2");
        assertEquals(dish.getName(), "Cabbage rolls");
    }

    private Restaurant getRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(9L);
        restaurant.setName("Mamma mia");
        restaurant.setStars(5);
        restaurant.setChefs(chefList());
        restaurant.setDishes(dishesList());

        return restaurant;
    }

    private List<Dish> dishesList() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish(2L, "Cabbage rolls"));
        dishes.add(new Dish(3L, "Steak"));
        dishes.add(new Dish(4L, "Pizza"));
        dishes.add(new Dish(5L, "Tiramisu"));
        dishes.add(new Dish(6L, "Omlet"));

        return dishes;
    }

    private List<Chef> chefList() {
        List<Chef> chefs = new ArrayList<>();
        chefs.add(new Chef(10L, "Alberto"));
        chefs.add(new Chef(11L, "Pierre"));
        chefs.add(new Chef(12L, "Carlos"));
        return chefs;
    }

    /// unit tests using streams

    //Find all dishes that have recipes
    @Test
    public void testGetDishesWithRecipes() {
        List<Dish> dishes = TestUtils.getDishList();
        List<Dish> filteredDishes = dishes.
            stream()
            .filter(d -> d.getRecipe() != null)
            .collect(Collectors.toList());
        Assertions.assertAll(
            () -> assertEquals("Apple pie", filteredDishes.get(0).getName()),
            () -> assertEquals("Risotto", filteredDishes.get(1).getName())
        );
    }

    //find all recipes with medium level difficulty
    @Test
    public void testRecipesOfMediumDifficulty() {
        List<Recipe> recipes = TestUtils.getRecipeList();
        List<Recipe> filteredRecipes = recipes
            .stream()
            .filter(r -> r.getDifficulty() == Difficulty.MEDIUM)
            .collect(Collectors.toList());
        Assertions.assertNotNull(filteredRecipes);
        Assertions.assertAll(
            () -> assertEquals("Risotto", filteredRecipes.get(0).getName()),
            () -> assertEquals("Onion soup", filteredRecipes.get(1).getName())
        );
    }

    //find all liquid ingredients from apple pie recipe
    @Test
    public void testLiquidIngredients() {
        List<Ingredient> ingredients = TestUtils.getApplePieIngredients();
        List<Ingredient> liquidIngredients = ingredients
            .stream()
            .filter(i -> i.getUnit() == Unit.LITER)
            .collect(Collectors.toList());
        Assertions.assertNotNull(liquidIngredients);
        Assertions.assertAll(
            () -> assertEquals("Melted Butter", liquidIngredients.get(0).getName()),
            () -> assertEquals("Vegetable oil", liquidIngredients.get(1).getName()),
            () -> assertEquals("Water", liquidIngredients.get(2).getName())
        );
    }

    //rename ingredients of risotto
    @Test
    public void testRenameRisottoIngredients() {
        List<Ingredient> ingredients = TestUtils.getRisottoIngredients();
        ingredients
            .stream()
            .forEach(
                i -> i.setName("Risotto " + i.getName())
            );
        Assertions.assertNotNull(ingredients);
        for (Ingredient i : ingredients) {
            Assertions.assertEquals(i.getName().substring(0, 7), "Risotto");
        }
    }

    //rename chefs names to uppercase
    @Test
    public void testRenameChefsNames() {
        List<Chef> chefList = TestUtils.getChefList();
        List<String> chefNames = chefList
            .stream()
            .map(c -> c.getName())
            .collect(Collectors.toList());
        List<String> newNames = new ArrayList<>();
        chefNames
            .stream()
            .map(n -> n.toUpperCase())
            .forEach(
                n2 -> newNames.add(n2)
            );
        Assertions.assertNotNull(chefNames);
        Assertions.assertAll(
            () -> assertEquals("EUGENE", newNames.get(0)),
            () -> assertEquals("STAN", newNames.get(1)),
            () -> assertEquals("THOR", newNames.get(2)),
            () -> assertEquals("LOKI", newNames.get(3)),
            () -> assertEquals("VADER", newNames.get(4))
        );
    }

}
