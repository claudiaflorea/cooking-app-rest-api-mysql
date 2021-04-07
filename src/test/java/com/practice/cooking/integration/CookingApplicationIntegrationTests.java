package com.practice.cooking.integration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.practice.cooking.utils.TestUtils.createDish;
import static com.practice.cooking.utils.TestUtils.getApplePieIngredients;
import static com.practice.cooking.utils.TestUtils.getChefList;
import static com.practice.cooking.utils.TestUtils.getDishList;
import static com.practice.cooking.utils.TestUtils.getRecipeList;
import static com.practice.cooking.utils.TestUtils.getRisottoIngredients;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.model.Unit;
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
        Chef chef = chefList().stream().findFirst().get();
        assertEquals(chef.getId().toString(), "10");
        assertEquals(chef.getName(), "Alberto");
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

    private Set<Dish> dishesList() {
        Set<Dish> dishes = new TreeSet<>();
        dishes.add(createDish("Cabbage rolls", null));
        dishes.add(createDish("Steak", null));
        dishes.add(createDish("Pizza", null));
        dishes.add(createDish("Tiramisu", null));
        dishes.add(createDish("Omlet", null));

        return dishes;
    }

    private Set<Chef> chefList() {
        Set<Chef> chefs = new HashSet<>();
        chefs.add(new Chef(10L, "Alberto"));
        chefs.add(new Chef(11L, "Pierre"));
        chefs.add(new Chef(12L, "Carlos"));
        return chefs;
    }

    /// unit tests using streams

    //Find all dishes that have recipes
    @Test
    public void testGetDishesWithRecipes() {
        List<Dish> dishes = getDishList();
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
        List<Recipe> recipes = getRecipeList();
        List<Recipe> filteredRecipes = recipes
            .stream()
            .filter(r -> r.getDifficulty() == Difficulty.MEDIUM)
            .collect(Collectors.toList());
        Assertions.assertNotNull(filteredRecipes);
        assertEquals("Risotto", filteredRecipes.get(0).getName());
    }

    //find all liquid ingredients from apple pie recipe
    @Test
    public void testLiquidIngredients() {
        List<Ingredient> ingredients = getApplePieIngredients();
        List<Ingredient> liquidIngredients = ingredients
            .stream()
            .filter(i -> i.getUnit() == Unit.LITER)
            .collect(Collectors.toList());
        Assertions.assertNotNull(liquidIngredients);
        Assertions.assertAll(
            () -> assertEquals("Melted Butter", liquidIngredients.get(0).getName()),
            () -> assertEquals("Vegetable oil", liquidIngredients.get(1).getName())
        );
    }

    //rename ingredients of risotto
    @Test
    public void testRenameRisottoIngredients() {
        List<Ingredient> ingredients = new ArrayList<>(getRisottoIngredients());
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
        List<Chef> chefList = new ArrayList<>(getChefList());
        List<String> chefNames = chefList
            .stream()
            .map(Chef::getName)
            .collect(Collectors.toList());
        List<String> newNames = new ArrayList<>();
        chefNames
            .stream()
            .map(String::toUpperCase)
            .forEach(
                newNames::add
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
