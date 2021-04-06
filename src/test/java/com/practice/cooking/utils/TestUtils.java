package com.practice.cooking.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.model.Unit;

public class TestUtils {

    private static final String APPLE         = "Apple";
    private static final String CINNAMON      = "Cinnamon";
    private static final String YEAST         = "Yeast";
    private static final String SUGAR         = "Sugar";
    private static final String MELTED_BUTTER = "Melted Butter";
    private static final String VEGETABLE_OIL = "Vegetable oil";
    private static final String WATER         = "Water";
    private static final String FLOUR         = "Flour";

    public static Set<Chef> getChefList() {
        Set<Chef> chefs = new TreeSet<>();
        chefs.add(new Chef(1L, "Eugene"));
        chefs.add(new Chef(2L, "Stan"));
        chefs.add(new Chef(3L, "Thor"));
        chefs.add(new Chef(4L, "Loki"));
        chefs.add(new Chef(5L, "Vader"));

        return chefs;
    }

    public static Set<Dish> getDishList() {
        Set<Dish> dishes = new TreeSet<>();
        dishes.add(new Dish(10L, "Apple pie", getRecipeList().stream().findFirst().get()));
        dishes.add(new Dish(11L, "Risotto", getRecipeList().stream().iterator().next()));
        dishes.add(new Dish(12L, "Mac'n'cheese", null));
        dishes.add(new Dish(13L, "Brownie", null));
        dishes.add(new Dish(14L, "Coleslaw", null));

        return dishes;
    }

    public static Set<Recipe> getRecipeList() {
        Set<Recipe> recipes = new TreeSet<>();
        recipes.add(new Recipe(1L, "Apple Pie", Difficulty.EASY, getApplePieIngredients(), 4, RecipeType.DESSERT));
        recipes.add(new Recipe(2L, "Risotto", Difficulty.MEDIUM, getRisottoIngredients(), 1, RecipeType.SIDE));
        recipes.add(new Recipe(3L, "Guacamole", Difficulty.EASY, getGuacamoleIngredients(), 1, RecipeType.SIDE));

        return recipes;
    }

    public static List<Restaurant> getRestaurantList() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(6L, "Als Seafood", 5, getDishList(), getChefList()));
        restaurants.add(new Restaurant(6L, "Bon appetit", 3, getDishList(), getChefList()));

        return restaurants;
    }
    
    public static Dish createDish(String name, Recipe recipe) {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setRecipe(recipe);
        
        return dish;
    }

    public static Restaurant createRestaurant(String name, Integer stars, Set<Dish> dishes, Set<Chef> chefs) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setChefs(chefs);
        restaurant.setDishes(dishes);
        restaurant.setStars(stars);
        
        return restaurant;
    }
     
    public static Set<Ingredient> getRisottoIngredients() {
        Set<Ingredient> risottoIngredients = new TreeSet<>();
        risottoIngredients.add(createIngredient("Rice", 1, Unit.KG));
        risottoIngredients.add(createIngredient("Salt", 0.001, Unit.KG));
        risottoIngredients.add(createIngredient("Pepper", 0.001, Unit.KG));
        risottoIngredients.add(createIngredient("Butter", 0.1, Unit.KG));

        return risottoIngredients;
    }

    public static Set<Ingredient> getApplePieIngredients() {
        Set<Ingredient> applePieIngredients = new TreeSet<>();
        applePieIngredients.add(createIngredient(APPLE, 5, Unit.KG));
        applePieIngredients.add(createIngredient(FLOUR, 2, Unit.KG));
        applePieIngredients.add(createIngredient(CINNAMON, 0.001, Unit.KG));
        applePieIngredients.add(createIngredient( YEAST, 0.001, Unit.KG));
        applePieIngredients.add(createIngredient(SUGAR, 0.01, Unit.KG));
        applePieIngredients.add(createIngredient(MELTED_BUTTER, 0.01, Unit.LITER));
        applePieIngredients.add(createIngredient(VEGETABLE_OIL, 0.001, Unit.LITER));
        applePieIngredients.add(createIngredient( WATER, 0.005, Unit.LITER));

        return applePieIngredients;
    }

    public static Set<Ingredient> getGuacamoleIngredients() {
        Set<Ingredient> guacamoleIngredients = new TreeSet<>();
        guacamoleIngredients.add(createIngredient("Avocado", 2, Unit.PIECE));
        guacamoleIngredients.add(createIngredient("Garlic", 2, Unit.KG));
        guacamoleIngredients.add(createIngredient("Olive oil", 0.001, Unit.LITER));

        return guacamoleIngredients;
    }

    public static Ingredient createIngredient(String name, double quantity, Unit unit) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ingredient.setUnit(unit);
        ingredient.setQuantity(quantity);

        return ingredient;
    }

    public static Ingredient createIngredientWithId(Long id, String name, double quantity, Unit unit) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setName(name);
        ingredient.setUnit(unit);
        ingredient.setQuantity(quantity);

        return ingredient;
    }
    
    public static Recipe createRecipe(String name, Difficulty difficulty, Set<Ingredient> ingredients, Integer cookingTime, RecipeType type) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setDifficulty(difficulty);
        recipe.setIngredients(ingredients);
        recipe.setCookingTime(cookingTime);
        recipe.setRecipeType(type);
        
        return recipe;
    }
}
