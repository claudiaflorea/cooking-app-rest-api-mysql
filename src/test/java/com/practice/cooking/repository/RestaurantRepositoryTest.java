package com.practice.cooking.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.practice.cooking.utils.TestUtils.createDish;
import static com.practice.cooking.utils.TestUtils.createIngredient;
import static com.practice.cooking.utils.TestUtils.createRecipe;
import static com.practice.cooking.utils.TestUtils.createRestaurant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.model.Unit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantRepositoryTest {

    private static final String ALS_SEAFOOD   = "Als Seafood";
    private static final String BON_APPETIT   = "Bon appetit";
    private static final String CHEF_EUGENE   = "Chef. Eugene";
    private static final String CHEF_STAN     = "Chef. Stan";
    private static final String APPLE         = "Apple";
    private static final String CINNAMON      = "Cinnamon";
    private static final String YEAST         = "Yeast";
    private static final String SUGAR         = "Sugar";
    private static final String MELTED_BUTTER = "Melted Butter";
    private static final String VEGETABLE_OIL = "Vegetable oil";
    private static final String WATER         = "Water";
    private static final String FLOUR         = "Flour";
    private static final String APPLE_PIE     = "Apple pie";
    private static final String AVOCADO       = "Avocado";
    private static final String GARLIC        = "Garlic";
    private static final String OLIVE_OIL     = "Olive oil";
    public static final  String GUACAMOLE     = "Guacamole";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void init() {
         
        Set<Chef> chefs = new TreeSet<>();
        chefs.add(new Chef(1L, CHEF_EUGENE));
        chefs.add(new Chef(2L, CHEF_STAN));

        Set<Ingredient> applePieIngredients = new TreeSet<>();
        applePieIngredients.add(createIngredient(APPLE, 5, Unit.KG));
        applePieIngredients.add(createIngredient(FLOUR, 2, Unit.KG));
        applePieIngredients.add(createIngredient(CINNAMON, 0.001, Unit.KG));
        applePieIngredients.add(createIngredient(YEAST, 0.001, Unit.KG));
        applePieIngredients.add(createIngredient(SUGAR, 0.01, Unit.KG));
        applePieIngredients.add(createIngredient(MELTED_BUTTER, 0.01, Unit.LITER));
        applePieIngredients.add(createIngredient(VEGETABLE_OIL, 0.001, Unit.LITER));
        applePieIngredients.add(createIngredient(WATER, 0.005, Unit.LITER));

        Set<Ingredient> guacamoleIngredients = new TreeSet<>();
        guacamoleIngredients.add(createIngredient(AVOCADO, 2, Unit.PIECE));
        guacamoleIngredients.add(createIngredient(GARLIC, 2, Unit.KG));
        guacamoleIngredients.add(createIngredient(OLIVE_OIL, 0.001, Unit.LITER));


        List<Recipe> recipes = new ArrayList<>();
        recipes.add(createRecipe(APPLE_PIE, Difficulty.EASY, applePieIngredients, 4, RecipeType.DESSERT));
        recipes.add(createRecipe(GUACAMOLE, Difficulty.EASY, guacamoleIngredients, 1, RecipeType.SIDE));

        Set<Dish> dishes = new TreeSet<>();
        dishes.add(createDish(APPLE_PIE, recipes.get(0)));
        dishes.add(createDish(GUACAMOLE, recipes.get(1)));

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(createRestaurant(ALS_SEAFOOD, 5, dishes, chefs));
        restaurants.add(createRestaurant(BON_APPETIT, 5, dishes, chefs));

        restaurantRepository.saveAll(restaurants);
    }

    @AfterEach
    void after() {
        restaurantRepository.deleteAll();
    }
    
    @Test
    public void testGetRestaurantsByName() {

        //when
        List<Restaurant> result = restaurantRepository.findAllByName(ALS_SEAFOOD);

        //then
        assertThat(result).hasSize(1);
        assertThat(result).element(0).returns(ALS_SEAFOOD, from(Restaurant::getName));
    }

    @Test
    public void testGetVegetarianRestaurants() {

        //when
        List<Restaurant> result = restaurantRepository.findAllByDishesNotContainingMeat();

        //then
        assertThat(result).hasSize(2);
        assertThat(result).element(0).returns(ALS_SEAFOOD, from(Restaurant::getName));
        assertThat(result).element(1).returns(BON_APPETIT, from(Restaurant::getName));

    }
}
