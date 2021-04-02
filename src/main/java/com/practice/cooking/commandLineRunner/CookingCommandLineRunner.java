package com.practice.cooking.commandLineRunner;

import java.util.Objects;
import java.util.stream.Collectors;

import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.dto.RestaurantDto;
import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.model.Unit;
import com.practice.cooking.service.ChefService;
import com.practice.cooking.service.DishService;
import com.practice.cooking.service.IngredientService;
import com.practice.cooking.service.RecipeService;
import com.practice.cooking.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookingCommandLineRunner implements CommandLineRunner {

    public static final String CHEF_NAME          = "Benjamin";
    public static final String AVOCADO_INGREDIENT = "Avocado";
    public static final String GUACAMOLE_RECIPE   = "Guacamole";
    public static final String RESTAURANT_NAME    = "Taco Bell";

    private final ChefService       chefService;
    private final IngredientService ingredientService;
    private final RecipeService     recipeService;
    private final DishService       dishService;
    private final RestaurantService restaurantService;

    private final ConversionService conversionService;

    @Override
    public void run(String... args) {

        //Adding a new record in Chef collection if there isn't any record with that name in the DB
        if (chefService.getAllByName(CHEF_NAME).size() == 0) {
            ChefDto chefDto = new ChefDto();
            chefDto.setName(CHEF_NAME);
            chefService.add(Objects.requireNonNull(conversionService.convert(chefDto, Chef.class)));
        }

        //Adding a new record in Ingredient collection if there isn't any record with that name in the DB
        Integer avocadoCounter = ingredientService.getAllByName(AVOCADO_INGREDIENT).size();
        if (avocadoCounter == 0) {
            IngredientDto ingredientDto = new IngredientDto();
            ingredientDto.setName(AVOCADO_INGREDIENT);
            ingredientDto.setUnit(Unit.PIECE);
            ingredientDto.setQuantity(2);
            ingredientService.add(Objects.requireNonNull(conversionService.convert(ingredientDto, Ingredient.class)));
        }

        //Adding a new record in Recipe collection if there isn't any record with that name in the DB
        Integer guacamoleRecipeCounter = recipeService.getAllByName(GUACAMOLE_RECIPE).size();
        if (guacamoleRecipeCounter == 0) {
            RecipeDto recipeDto = new RecipeDto();
            recipeDto.setName(GUACAMOLE_RECIPE);
            recipeDto.setCookingTime(1);
            recipeDto.setRecipeType(RecipeType.ANTRE);
            recipeDto.setDifficulty(Difficulty.EASY);
            recipeDto.setIngredients(
                avocadoCounter > 0
                    ?
                    ingredientService.getAllByName(AVOCADO_INGREDIENT).stream()
                        .map(i -> conversionService.convert(i, IngredientDto.class))
                        .collect(Collectors.toList())
                    : null
            );
            recipeService.add(Objects.requireNonNull(conversionService.convert(recipeDto, Recipe.class)));
        }

        //Adding a new record in Dish collection if there isn't any record with that name in the DB
        Integer guacamoleDishCounter = dishService.getAllByName(GUACAMOLE_RECIPE).size();
        if (guacamoleDishCounter == 0) {
            DishDto dishDto = new DishDto();
            dishDto.setName(GUACAMOLE_RECIPE);
            dishDto.setRecipe(
                guacamoleRecipeCounter > 0
                    ?
                    recipeService.getAllRecipesThatContainAvocado().stream()
                        .map(r -> conversionService.convert(r, RecipeDto.class))
                        .collect(Collectors.toList()).get(0)
                    :
                    null
            );
            dishService.add(Objects.requireNonNull(conversionService.convert(dishDto, Dish.class)));
        }

        //Adding a new record in Restaurant collection if there isn't any record with that name in the DB
        Integer restaurantCounter = restaurantService.getAllByName(RESTAURANT_NAME).size();
        if (restaurantCounter == 0) {
            RestaurantDto restaurantDto = new RestaurantDto();
            restaurantDto.setName(RESTAURANT_NAME);
            restaurantDto.setStars(4);
            restaurantDto.setChefs(
                chefService.getAllByName(CHEF_NAME).size() > 0
                    ?
                    chefService.getAllByName(CHEF_NAME)
                        .stream()
                        .map(c -> conversionService.convert(c, ChefDto.class))
                        .collect(Collectors.toList())
                    :
                    null
            );
            restaurantDto.setDishes(
                guacamoleDishCounter > 0
                    ?
                    dishService.getAllByName(GUACAMOLE_RECIPE).stream()
                        .map(d -> conversionService.convert(d, DishDto.class))
                        .collect(Collectors.toList())
                    :
                    null
            );
            restaurantService.add(Objects.requireNonNull(conversionService.convert(restaurantDto, Restaurant.class)));
        }
        
        //test recipe repository findAllByRecipeType method
        recipeService.getAllByRecipeType(RecipeType.ANTRE).stream()
            .map(recipe -> conversionService.convert(recipe, RecipeDto.class))
            .forEach(System.out::println);
        
        //test restaurant repository method findAllByDishesNotContainingMeat
        restaurantService.getAllVegetarianRestaurants().stream()
            .map(restaurant -> conversionService.convert(restaurant, RestaurantDto.class))
            .forEach(System.out::println);

        //test restaurant repository method findAllByRecipeContainingLiquidIngredients
        dishService.getDishesWithLiquidIngredients().stream()
            .map(dish -> conversionService.convert(dish, DishDto.class))
            .forEach(System.out::println);
        
        //test chef repository method findAllByNameStartingWithChefPrefix
        chefService.getAllByNameStartingWithChefPrefix().stream()
            .map(chef -> conversionService.convert(chef, ChefDto.class))
            .forEach(System.out::println);
        
        //test ingredient repository method findAllByHeavierThan1Kg
        ingredientService.getAllHeavierThan1Kg().stream()
            .map(ingredient -> conversionService.convert(ingredient, IngredientDto.class))
            .forEach(System.out::println);
    }
    
}
