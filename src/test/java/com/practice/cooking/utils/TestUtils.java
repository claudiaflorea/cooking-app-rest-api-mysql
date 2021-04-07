package com.practice.cooking.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.model.RestaurantToChef;
import com.practice.cooking.model.RestaurantToDish;
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

    public static List<Dish> getDishList() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(createDish("Apple pie", getRecipeList().get(0)));
        dishes.add(createDish("Risotto", getRecipeList().get(1)));
        dishes.add(createDish("Mac'n'cheese", null));
        dishes.add(createDish("Brownie", null));
        dishes.add(createDish("Coleslaw", null));

        return dishes;
    }

    public static List<Recipe> getRecipeList() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(createRecipe("Apple Pie", Difficulty.EASY, 4, RecipeType.DESSERT));
        recipes.add(createRecipe("Risotto", Difficulty.MEDIUM, 1, RecipeType.SIDE));
        recipes.add(createRecipe("Guacamole", Difficulty.EASY, 1, RecipeType.SIDE));

        return recipes;
    }

    public static List<Restaurant> getRestaurantList() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(6L, "Als Seafood", 5, new HashSet<>(getDishList()), getChefList()));
        restaurants.add(new Restaurant(6L, "Bon appetit", 3, new HashSet<>(getDishList()), getChefList()));

        return restaurants;
    }

    public static Dish createDish(String name, Recipe recipe) {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setRecipe(recipe);
        
        return dish;
    }

    public static Dish createSimpleDish(String name) {
        Dish dish = new Dish();
        dish.setName(name);
    
        return dish;
    }

    public static Dish createDishWithId(Long id, String name, Recipe recipe) {
        Dish dish = new Dish();
        dish.setId(id);
        dish.setName(name);
        if (recipe != null) {
            dish.setRecipe(recipe);
        }

        return dish;
    }
    
    public static Chef createChef(String name) {
        Chef chef = new Chef();
        chef.setName(name);
        
        return chef;
    }
    
    public static RestaurantToChef createRestaurantToChefLink(Restaurant restaurant, Set<Chef> chefs) {
        RestaurantToChef restaurantToChef = new RestaurantToChef();
        restaurantToChef.setRestaurant(restaurant);
        restaurant.setChefs(chefs);
        
        return restaurantToChef;
    }

    public static RestaurantToDish createRestaurantToDishLink(Restaurant restaurant, Set<Dish> dishes) {
        RestaurantToDish restaurantToDish = new RestaurantToDish();
        restaurantToDish.setRestaurant(restaurant);
        restaurant.setDishes(dishes);

        return restaurantToDish;
    }

    public static Restaurant createRestaurant(String name, Integer stars) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setStars(stars);

        return restaurant;
    }

    public static Set<Ingredient> getRisottoIngredients() {
        Set<Ingredient> risottoIngredients = new TreeSet<>();
        risottoIngredients.add(createIngredient("Rice", 1, Unit.KG, getRecipeList().get(1)));
        risottoIngredients.add(createIngredient("Salt", 0.001, Unit.KG, getRecipeList().get(1)));
        risottoIngredients.add(createIngredient("Pepper", 0.001, Unit.KG, getRecipeList().get(1)));
        risottoIngredients.add(createIngredient("Butter", 0.1, Unit.KG, getRecipeList().get(1)));

        return risottoIngredients;
    }

    public static List<Ingredient> getApplePieIngredients() {
        List<Ingredient> applePieIngredients = new ArrayList<>();
        applePieIngredients.add(createIngredient(APPLE, 5, Unit.KG, getRecipeList().get(0)));
        applePieIngredients.add(createIngredient(FLOUR, 2, Unit.KG, getRecipeList().get(0)));
        applePieIngredients.add(createIngredient(CINNAMON, 0.001, Unit.KG, getRecipeList().get(0)));
        applePieIngredients.add(createIngredient(YEAST, 0.001, Unit.KG, getRecipeList().get(0)));
        applePieIngredients.add(createIngredient(SUGAR, 0.01, Unit.KG, getRecipeList().get(0)));
        applePieIngredients.add(createIngredient(MELTED_BUTTER, 0.01, Unit.LITER, getRecipeList().get(0)));
        applePieIngredients.add(createIngredient(VEGETABLE_OIL, 0.001, Unit.LITER, getRecipeList().get(0)));
        applePieIngredients.add(createIngredient(WATER, 0.005, Unit.LITER, getRecipeList().get(0)));

        return applePieIngredients;
    }

    public static Set<Ingredient> getGuacamoleIngredients() {
        Set<Ingredient> guacamoleIngredients = new TreeSet<>();
        guacamoleIngredients.add(createIngredient("Avocado", 2, Unit.PIECE, getRecipeList().get(2)));
        guacamoleIngredients.add(createIngredient("Garlic", 2, Unit.KG, getRecipeList().get(2)));
        guacamoleIngredients.add(createIngredient("Olive oil", 0.001, Unit.LITER, getRecipeList().get(2)));

        return guacamoleIngredients;
    }

    public static Ingredient createIngredient(String name, double quantity, Unit unit, Recipe recipe) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ingredient.setUnit(unit);
        ingredient.setQuantity(quantity);
        ingredient.setRecipe(recipe);

        return ingredient;
    }

    public static Ingredient createSimpleIngredient(String name, double quantity, Unit unit) {
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

    public static Recipe createRecipe(String name, Difficulty difficulty, Integer cookingTime, RecipeType type) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setDifficulty(difficulty);
        recipe.setCookingTime(cookingTime);
        recipe.setRecipeType(type);

        return recipe;
    }
}
