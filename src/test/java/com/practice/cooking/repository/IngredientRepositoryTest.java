package com.practice.cooking.repository;

import java.util.ArrayList;
import java.util.List;

import static com.practice.cooking.utils.TestUtils.createSimpleIngredient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import com.practice.cooking.model.Ingredient;
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
public class IngredientRepositoryTest {

    private static final String APPLE         = "Apple";
    private static final String CINNAMON      = "Cinnamon";
    private static final String YEAST         = "Yeast";
    private static final String SUGAR         = "Sugar";
    private static final String MELTED_BUTTER = "Melted Butter";
    private static final String VEGETABLE_OIL = "Vegetable oil";
    private static final String WATER         = "Water";
    private static final String FLOUR         = "Flour";

    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    void init() {
        List<Ingredient> applePieIngredients = new ArrayList<>();
        applePieIngredients.add(createSimpleIngredient(APPLE, 5, Unit.KG));
        applePieIngredients.add(createSimpleIngredient(FLOUR, 2, Unit.KG));
        applePieIngredients.add(createSimpleIngredient(CINNAMON, 0.001, Unit.KG));
        applePieIngredients.add(createSimpleIngredient(YEAST, 0.001, Unit.KG));
        applePieIngredients.add(createSimpleIngredient(SUGAR, 0.01, Unit.KG));
        applePieIngredients.add(createSimpleIngredient(MELTED_BUTTER, 0.01, Unit.LITER));
        applePieIngredients.add(createSimpleIngredient(VEGETABLE_OIL, 0.001, Unit.LITER));
        applePieIngredients.add(createSimpleIngredient(WATER, 0.005, Unit.LITER));
        applePieIngredients.add(createSimpleIngredient(APPLE, 7, Unit.KG));

        ingredientRepository.saveAll(applePieIngredients);
    }

    @AfterEach
    void after() {
        ingredientRepository.deleteAll();
    }

    @Test
    public void testGetIngredientByName() {

        //when
        List<Ingredient> result = ingredientRepository.findAllByName(APPLE);

        //then
        assertThat(result).hasSize(2);
        assertThat(result).element(0).returns(APPLE, from(Ingredient::getName));
        assertThat(result).element(1).returns(APPLE, from(Ingredient::getName));

    }

    @Test
    public void testGetIngredientsHeavierThan1Kg() {
        //when
        List<Ingredient> result = ingredientRepository.findAllHeavierThan1Kg();

        //then
        assertThat(result).hasSize(3);
        assertThat(result).element(0).returns(APPLE, from(Ingredient::getName));
        assertThat(result).element(1).returns(FLOUR, from(Ingredient::getName));
        assertThat(result).element(2).returns(APPLE, from(Ingredient::getName));

    }
}
