package com.practice.cooking.repository;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.practice.cooking.utils.TestUtils.createDish;
import static com.practice.cooking.utils.TestUtils.createIngredient;
import static com.practice.cooking.utils.TestUtils.createRecipe;
import static java.util.Arrays.asList;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DishRepositoryTest {

    public static final  String APPLE_PIE     = "Apple pie";
    public static final  String RISOTTO       = "Risotto";
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
    private DishRepository dishRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    void init() {

        Recipe applePie = createRecipe(APPLE_PIE, Difficulty.EASY, 4, RecipeType.DESSERT);
        Recipe risotto = createRecipe(RISOTTO, Difficulty.MEDIUM, 1, RecipeType.SIDE);
        Recipe guacamole = createRecipe(GUACAMOLE, Difficulty.EASY, 1, RecipeType.SIDE);
        Recipe applePie2 = createRecipe(APPLE_PIE, Difficulty.EASY, 4, RecipeType.DESSERT);
        recipeRepository.saveAll(asList(applePie, risotto, guacamole, applePie2));

        Set<Ingredient> applePieIngredients = new TreeSet<>();
        applePieIngredients.add(createIngredient(APPLE, 5, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(FLOUR, 2, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(CINNAMON, 0.001, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(YEAST, 0.001, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(SUGAR, 0.01, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(MELTED_BUTTER, 0.01, Unit.LITER, applePie));
        applePieIngredients.add(createIngredient(VEGETABLE_OIL, 0.001, Unit.LITER, applePie));
        applePieIngredients.add(createIngredient(WATER, 0.005, Unit.LITER, applePie));
        ingredientRepository.saveAll(applePieIngredients);

        Set<Ingredient> guacamoleIngredients = new TreeSet<>();
        guacamoleIngredients.add(createIngredient(AVOCADO, 2, Unit.PIECE, guacamole));
        guacamoleIngredients.add(createIngredient(GARLIC, 2, Unit.KG, guacamole));
        guacamoleIngredients.add(createIngredient(OLIVE_OIL, 0.001, Unit.LITER, guacamole));
        ingredientRepository.saveAll(guacamoleIngredients);

        Set<Ingredient> risottoIngredients = new TreeSet<>();
        risottoIngredients.add(createIngredient(RICE, 1, Unit.KG, risotto));
        risottoIngredients.add(createIngredient(SALT, 0.001, Unit.KG, risotto));
        risottoIngredients.add(createIngredient(PEPPER, 0.001, Unit.KG, risotto));
        risottoIngredients.add(createIngredient(BUTTER, 0.1, Unit.KG, risotto));
        ingredientRepository.saveAll(risottoIngredients);

        Dish applePieDish = createDish(APPLE_PIE, applePie);
        Dish risottoDish = createDish(RISOTTO, risotto);
        Dish guacamoleDish = createDish(GUACAMOLE, guacamole);
        Dish applePieDish2 = createDish(APPLE_PIE, guacamole);
        dishRepository.saveAll(asList(applePieDish, risottoDish, guacamoleDish, applePieDish2));

    }

    @AfterEach
    void after() {
        dishRepository.deleteAll();
        ingredientRepository.deleteAll();
        recipeRepository.deleteAll();
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
