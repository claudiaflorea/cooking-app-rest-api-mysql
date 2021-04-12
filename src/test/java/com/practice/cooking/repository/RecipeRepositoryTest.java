package com.practice.cooking.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Tuple;

import static com.practice.cooking.utils.TestUtils.createIngredient;
import static com.practice.cooking.utils.TestUtils.createRecipe;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.model.Unit;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RecipeRepositoryTest {

    private static final String APPLE_PIE = "Apple Pie";
    private static final String RISOTTO   = "Risotto";
    public static final  String GUACAMOLE     = "Guacamole";
    private static final String APPLE         = "Apple";
    private static final String FLOUR         = "Flour";
    private static final String CINNAMON      = "Cinnamon";
    private static final String YEAST         = "Yeast";
    private static final String SUGAR         = "Sugar";
    private static final String MELTED_BUTTER = "Melted Butter";
    private static final String VEGETABLE_OIL = "Vegetable oil";
    private static final String WATER         = "Water";
    private static final String AVOCADO       = "Avocado";
    private static final String GARLIC        = "Garlic";
    private static final String OLIVE_OIL     = "Olive oil";
    private static final String RICE          = "Rice";
    private static final String SALT          = "Salt";
    private static final String PEPPER        = "Pepper";
    private static final String BUTTER        = "Butter";

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    void init() {

        Recipe applePie = createRecipe(APPLE_PIE, Difficulty.EASY, 4, RecipeType.DESSERT);
        Recipe risotto = createRecipe(RISOTTO, Difficulty.MEDIUM, 1, RecipeType.SIDE);
        Recipe guacamole = createRecipe(GUACAMOLE, Difficulty.EASY, 1, RecipeType.SIDE);
        Recipe applePie2 = createRecipe(APPLE_PIE, Difficulty.EASY, 4, RecipeType.DESSERT);
        recipeRepository.saveAll(Arrays.asList(applePie, risotto, guacamole, applePie2));

        List<Ingredient> applePieIngredients = new ArrayList<>();
        applePieIngredients.add(createIngredient(APPLE, 5, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(FLOUR, 2, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(CINNAMON, 0.001, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(YEAST, 0.001, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(SUGAR, 0.01, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(MELTED_BUTTER, 0.01, Unit.LITER, applePie));
        applePieIngredients.add(createIngredient(VEGETABLE_OIL, 0.001, Unit.LITER, applePie));
        applePieIngredients.add(createIngredient(WATER, 0.005, Unit.LITER, applePie));
        ingredientRepository.saveAll(applePieIngredients);

        List<Ingredient> guacamoleIngredients = new ArrayList<>();
        guacamoleIngredients.add(createIngredient(AVOCADO, 2, Unit.PIECE, guacamole));
        guacamoleIngredients.add(createIngredient(GARLIC, 2, Unit.KG, guacamole));
        guacamoleIngredients.add(createIngredient(OLIVE_OIL, 0.001, Unit.LITER, guacamole));
        ingredientRepository.saveAll(guacamoleIngredients);

        List<Ingredient> risottoIngredients = new ArrayList<>();
        risottoIngredients.add(createIngredient(RICE, 1, Unit.KG, risotto));
        risottoIngredients.add(createIngredient(SALT, 0.001, Unit.KG, risotto));
        risottoIngredients.add(createIngredient(PEPPER, 0.001, Unit.KG, risotto));
        risottoIngredients.add(createIngredient(BUTTER, 0.1, Unit.KG, risotto));
        ingredientRepository.saveAll(risottoIngredients);
    }

    @AfterEach
    void after() {
        ingredientRepository.deleteAll();
        recipeRepository.deleteAll();
    }

    @Test
    public void testGetRecipeByName() {
        //when
        List<Recipe> result = recipeRepository.findAllByName(APPLE_PIE);

        //then
        assertThat(result).hasSize(2);
        assertThat(result).element(0).returns(APPLE_PIE, from(Recipe::getName));
        assertThat(result).element(1).returns(APPLE_PIE, from(Recipe::getName));

    }

    @Test
    public void testGetRecipesWithAvocados() {

        //when
        List<Recipe> result = recipeRepository.findAllByIngredientsContainingAvocado();

        //then
        assertThat(result).hasSize(1);
        assertThat(result).element(0).returns(GUACAMOLE, from(Recipe::getName));

    }
    
    @Test
    public void testGetRecipesWithCarbohydrates() {
        //when
        List<Tuple> result = recipeRepository.findAllByIngredientsContainingCarbohydrates();
        
        //then
        assertThat(result).hasSize(2);
        assertEquals(APPLE_PIE, result.get(0).get(1));
        assertEquals(FLOUR, result.get(0).get(6));
        assertEquals(RISOTTO, result.get(1).get(1));
        assertEquals(RICE, result.get(1).get(6));
              
    }
}
