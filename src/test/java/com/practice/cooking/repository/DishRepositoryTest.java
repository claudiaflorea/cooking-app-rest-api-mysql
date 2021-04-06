package com.practice.cooking.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.practice.cooking.utils.TestUtils.createDish;
import static com.practice.cooking.utils.TestUtils.createIngredient;
import static com.practice.cooking.utils.TestUtils.createRecipe;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
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
public class DishRepositoryTest {

    public static final String APPLE_PIE = "Apple pie";
    public static final String RISOTTO   = "Risotto";
    public static final String GUACAMOLE = "Guacamole";


    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    void init() {
        Set<Ingredient> applePieIngredients = new TreeSet<>();
        applePieIngredients.add(createIngredient("Apple", 5, Unit.KG));
        applePieIngredients.add(createIngredient("Flour", 2, Unit.KG));
        applePieIngredients.add(createIngredient("Cinnamon", 0.001, Unit.KG));
        applePieIngredients.add(createIngredient("Yeast", 0.001, Unit.KG));
        applePieIngredients.add(createIngredient("Sugar", 0.01, Unit.KG));
        applePieIngredients.add(createIngredient("Melted Butter", 0.01, Unit.LITER));
        applePieIngredients.add(createIngredient("Vegetable oil", 0.001, Unit.LITER));
        applePieIngredients.add(createIngredient("Water", 0.005, Unit.LITER));

        Set<Ingredient> guacamoleIngredients = new TreeSet<>();
        guacamoleIngredients.add(createIngredient("Avocado", 2, Unit.PIECE));
        guacamoleIngredients.add(createIngredient("Garlic", 2, Unit.KG));
        guacamoleIngredients.add(createIngredient("Olive oil", 0.001, Unit.LITER));

        Set<Ingredient> risottoIngredients = new TreeSet<>();
        risottoIngredients.add(createIngredient("Rice", 1, Unit.KG));
        risottoIngredients.add(createIngredient("Salt", 0.001, Unit.KG));
        risottoIngredients.add(createIngredient("Pepper", 0.001, Unit.KG));
        risottoIngredients.add(createIngredient("Butter", 0.1, Unit.KG));

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(createRecipe(APPLE_PIE, Difficulty.EASY, applePieIngredients, 4, RecipeType.DESSERT));
        recipes.add(createRecipe(RISOTTO, Difficulty.MEDIUM, risottoIngredients, 1, RecipeType.SIDE));
        recipes.add(createRecipe(GUACAMOLE, Difficulty.EASY, guacamoleIngredients, 1, RecipeType.SIDE));

        List<Dish> dishes = new ArrayList<>();
        dishes.add(createDish(APPLE_PIE, recipes.get(0)));
        dishes.add(createDish("Risotto", recipes.get(1)));
        dishes.add(createDish( "Guacamole", recipes.get(2)));
        dishes.add(createDish(APPLE_PIE, recipes.get(0)));

//        ingredientRepository.saveAll(applePieIngredients);
//        ingredientRepository.saveAll(risottoIngredients);
//        ingredientRepository.saveAll(guacamoleIngredients);
//        recipeRepository.saveAll(recipes);
        dishRepository.saveAll(dishes);

    }

    @AfterEach
    void after() {
        dishRepository.deleteAll();
    }

    @Test
    public void testGetDishByName() {

        //when
        List<Dish> result = dishRepository.findAllByName(APPLE_PIE);

        //then
        assertThat(result).hasSize(2);
        assertThat(result).element(0).returns(APPLE_PIE, from(Dish::getName));
        assertThat(result).element(1).returns(APPLE_PIE, from(Dish::getName));

    }

    @Test
    public void testGetDishesWithLiquidIngredients() {
        //when
        List<Dish> result = dishRepository.findAllByRecipeContainingLiquidIngredients();

        //then
        assertThat(result).hasSize(3);
    }

}
