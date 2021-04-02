package com.practice.cooking.commandLineRunner;

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
        Long chefCounter = chefService.getAll().stream()
            .filter(chef -> chef.getName().equals(CHEF_NAME)).count();
        if (chefCounter == 0) {
            ChefDto chefDto = new ChefDto();
            chefDto.setName(CHEF_NAME);
            chefService.add(conversionService.convert(chefDto, Chef.class));
        }

        //Adding a new record in Ingredient collection if there isn't any record with that name in the DB
        Long avocadoCounter = ingredientService.getAll().stream()
            .filter(ingredient -> ingredient.getName().equals(AVOCADO_INGREDIENT))
            .count();
        if (avocadoCounter == 0) {
            IngredientDto ingredientDto = new IngredientDto();
            ingredientDto.setName(AVOCADO_INGREDIENT);
            ingredientDto.setUnit(Unit.PIECE);
            ingredientDto.setQuantity(2);
            ingredientService.add(conversionService.convert(ingredientDto, Ingredient.class));
        }

        //Adding a new record in Recipe collection if there isn't any record with that name in the DB
        Long guacamoleRecipeCounter = recipeService.getAll().stream()
            .filter(recipe -> recipe.getName().equals(GUACAMOLE_RECIPE))
            .count();
        if (guacamoleRecipeCounter == 0) {
            RecipeDto recipeDto = new RecipeDto();
            recipeDto.setName(GUACAMOLE_RECIPE);
            recipeDto.setCookingTime(1);
            recipeDto.setRecipeType(RecipeType.ANTRE);
            recipeDto.setDifficulty(Difficulty.EASY);
            recipeDto.setIngredients(
                avocadoCounter > 0
                    ?
                    ingredientService.getAll().stream()
                        .filter(ingredient -> ingredient.getName().equals(AVOCADO_INGREDIENT))
                        .map(i -> conversionService.convert(i, IngredientDto.class))
                        .collect(Collectors.toList())
                    : null
            );
            recipeService.add(conversionService.convert(recipeDto, Recipe.class));
        }

        //Adding a new record in Dish collection if there isn't any record with that name in the DB
        Long guacamoleDishCounter = dishService.getAll().stream()
            .filter(dish -> dish.getName().equals(GUACAMOLE_RECIPE))
            .count();
        if (guacamoleDishCounter == 0) {
            DishDto dishDto = new DishDto();
            dishDto.setName(GUACAMOLE_RECIPE);
            dishDto.setRecipe(
                guacamoleRecipeCounter > 0
                    ?
                    recipeService.getAll().stream()
                        .filter(recipe -> recipe.getName().equals(GUACAMOLE_RECIPE))
                        .map(r -> conversionService.convert(r, RecipeDto.class))
                        .collect(Collectors.toList()).get(0)
                    :
                    null
            );
            dishService.add(conversionService.convert(dishDto, Dish.class));
        }

        //Adding a new record in Restaurant collection if there isn't any record with that name in the DB
        Long restaurantCounter = restaurantService.getAll().stream()
            .filter(restaurant -> restaurant.getName().equals(RESTAURANT_NAME))
            .count();
        if (restaurantCounter== 0) {
            RestaurantDto restaurantDto = new RestaurantDto();
            restaurantDto.setName(RESTAURANT_NAME);
            restaurantDto.setStars(4);
            restaurantDto.setChefs(
                chefCounter > 0
                    ?
                    chefService.getAll().stream().filter(chef -> chef.getName().equals(CHEF_NAME))
                        .map(c -> conversionService.convert(c, ChefDto.class))
                        .collect(Collectors.toList())
                    :
                    null
            );
            restaurantDto.setDishes(
                guacamoleDishCounter > 0
                    ?
                    dishService.getAll().stream().filter(dish -> dish.getName().equals(GUACAMOLE_RECIPE))
                        .map(d -> conversionService.convert(d, DishDto.class))
                        .collect(Collectors.toList())
                    :
                    null
            );
            restaurantService.add(conversionService.convert(restaurantDto, Restaurant.class));
        }

    }

}
