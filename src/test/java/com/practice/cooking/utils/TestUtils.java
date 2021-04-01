package com.practice.cooking.utils;

import java.util.ArrayList;
import java.util.List;

import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.model.Unit;

public class TestUtils {

    public static List<Chef> getChefList() {
        List<Chef> chefs = new ArrayList<>();
        chefs.add(new Chef(1L, "Eugene"));
        chefs.add(new Chef(2L, "Stan"));
        chefs.add(new Chef(3L, "Thor"));
        chefs.add(new Chef(4L, "Loki"));
        chefs.add(new Chef(5L, "Vader"));

        return chefs;
    }

    public static List<Dish> getDishList() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish(10L, "Apple pie", getRecipeList().get(0)));
        dishes.add(new Dish(11L, "Risotto", getRecipeList().get(1)));
        dishes.add(new Dish(12L, "Mac'n'cheese"));
        dishes.add(new Dish(13L, "Brownie"));
        dishes.add(new Dish(14L, "Coleslaw"));

        return dishes;
    }

    public static List<Recipe> getRecipeList() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(1L, "Apple Pie", Difficulty.EASY, getApplePieIngredients(), 4, RecipeType.DESSERT));
        recipes.add(new Recipe(2L, "Risotto", Difficulty.MEDIUM, getRisottoIngredients(), 1, RecipeType.SIDE));
        recipes.add(new Recipe(3L, "Onion soup", Difficulty.MEDIUM, null, 2, RecipeType.MAIN_COURSE));
        recipes.add(new Recipe(4L, "Creme brulee", Difficulty.HARD, null, 3, RecipeType.DESSERT));
        recipes.add(new Recipe(5L, "Fois-gras", Difficulty.HARD, null, 5, RecipeType.ANTRE));

        return recipes;
    }
    
    public static List<Restaurant> getRestaurantList() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(6L, "Als Seafood", 5, getDishList(), getChefList()));
        restaurants.add(new Restaurant(6L, "Bon appetit", 3, getDishList(), getChefList()));

        return restaurants;
    }

    public static List<Ingredient> getRisottoIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(20L, "Rice", 1, Unit.KG));
        ingredients.add(new Ingredient(21L, "Salt", 0.001, Unit.KG));
        ingredients.add(new Ingredient(22L, "Pepper", 0.001, Unit.KG));
        ingredients.add(new Ingredient(25L, "Butter", 0.1, Unit.KG));

        return ingredients;
    }

    public static List<Ingredient> getApplePieIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(20L, "Apple", 1, Unit.KG));
        ingredients.add(new Ingredient(21L, "Flour", 1, Unit.KG));
        ingredients.add(new Ingredient(22L, "Cinnamon", 0.001, Unit.KG));
        ingredients.add(new Ingredient(23L, "Yeast", 0.001, Unit.KG));
        ingredients.add(new Ingredient(24L, "Sugar", 0.01, Unit.KG));
        ingredients.add(new Ingredient(25L, "Melted Butter", 0.01, Unit.LITER));
        ingredients.add(new Ingredient(26L, "Vegetable oil", 0.001, Unit.LITER));
        ingredients.add(new Ingredient(26L, "Water", 0.005, Unit.LITER));

        return ingredients;
    }

}
