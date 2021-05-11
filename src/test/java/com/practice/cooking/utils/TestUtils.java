package com.practice.cooking.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.dto.RecipeDto;
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
    private static final String EUGENE        = "Eugene";
    private static final String STAN          = "Stan";
    private static final String THOR          = "Thor";
    private static final String LOKI          = "Loki";
    private static final String VADER         = "Vader";
    private static final String APPLE_PIE     = "Apple pie";
    private static final String RISOTTO       = "Risotto";
    private static final String MAC_N_CHEESE  = "Mac'n'cheese";
    private static final String BROWNIE       = "Brownie";
    private static final String COLESLAW      = "Coleslaw";
    private static final String GUACAMOLE     = "Guacamole";
    private static final String ALS_SEAFOOD   = "Als Seafood";
    private static final String BON_APPETIT   = "Bon appetit";
    private static final String RICE          = "Rice";
    private static final String SALT          = "Salt";
    private static final String PEPPER        = "Pepper";
    private static final String BUTTER        = "Butter";
    private static final String AVOCADO       = "Avocado";
    private static final String GARLIC        = "Garlic";
    private static final String OLIVE_OIL     = "Olive oil";

    public static Set<Chef> getChefList() {
        Set<Chef> chefs = new TreeSet<>();
        chefs.add(new Chef(1L, EUGENE));
        chefs.add(new Chef(2L, STAN));
        chefs.add(new Chef(3L, THOR));
        chefs.add(new Chef(4L, LOKI));
        chefs.add(new Chef(5L, VADER));

        return chefs;
    }

    public static List<Dish> getDishList() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(createDish(APPLE_PIE, getRecipeList().get(0)));
        dishes.add(createDish(RISOTTO, getRecipeList().get(1)));
        dishes.add(createDish(MAC_N_CHEESE, null));
        dishes.add(createDish(BROWNIE, null));
        dishes.add(createDish(COLESLAW, null));

        return dishes;
    }

    public static List<Recipe> getRecipeList() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(createRecipe(APPLE_PIE, Difficulty.EASY, 4, RecipeType.DESSERT));
        recipes.add(createRecipe(RISOTTO, Difficulty.MEDIUM, 1, RecipeType.SIDE));
        recipes.add(createRecipe(GUACAMOLE, Difficulty.EASY, 1, RecipeType.SIDE));

        return recipes;
    }

    public static List<Restaurant> getRestaurantList() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(6L, ALS_SEAFOOD, 5, new HashSet<>(getDishList()), getChefList()));
        restaurants.add(new Restaurant(6L, BON_APPETIT, 3, new HashSet<>(getDishList()), getChefList()));

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

    public static DishDto createDishWithId(Long id, String name, RecipeDto recipe) {
        DishDto dish = new DishDto();
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

    public static RestaurantToChef createRestaurantToChefLink(Restaurant restaurant, Chef chef) {
        RestaurantToChef restaurantToChef = new RestaurantToChef();
        restaurantToChef.setRestaurant(restaurant);
        restaurantToChef.setChef(chef);

        return restaurantToChef;
    }

    public static RestaurantToDish createRestaurantToDishLink(Restaurant restaurant, Dish dish) {
        RestaurantToDish restaurantToDish = new RestaurantToDish();
        restaurantToDish.setRestaurant(restaurant);
        restaurantToDish.setDish(dish);

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
        risottoIngredients.add(createIngredient(RICE, 1, Unit.KG, getRecipeList().get(1)));
        risottoIngredients.add(createIngredient(SALT, 0.001, Unit.KG, getRecipeList().get(1)));
        risottoIngredients.add(createIngredient(PEPPER, 0.001, Unit.KG, getRecipeList().get(1)));
        risottoIngredients.add(createIngredient(BUTTER, 0.1, Unit.KG, getRecipeList().get(1)));

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
        guacamoleIngredients.add(createIngredient(AVOCADO, 2, Unit.PIECE, getRecipeList().get(2)));
        guacamoleIngredients.add(createIngredient(GARLIC, 2, Unit.KG, getRecipeList().get(2)));
        guacamoleIngredients.add(createIngredient(OLIVE_OIL, 0.001, Unit.LITER, getRecipeList().get(2)));

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

    public static IngredientDto createIngredientWithId(Long id, String name, double quantity, Unit unit) {
        IngredientDto ingredient = new IngredientDto();
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
    
    public static ChefDto createChefDto(Long id, String name) {
        ChefDto chefDto = new ChefDto();
        chefDto.setId(id);
        chefDto.setName(name);
        
        return chefDto;
    }
}
