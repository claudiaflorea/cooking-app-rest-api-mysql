package com.practice.cooking.repository;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.practice.cooking.utils.TestUtils.createIngredient;
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
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
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
        Set<Ingredient> applePieIngredients = new TreeSet<>();
        applePieIngredients.add(createIngredient(APPLE, 5, Unit.KG));
        applePieIngredients.add(createIngredient(FLOUR, 2, Unit.KG));
        applePieIngredients.add(createIngredient(CINNAMON, 0.001, Unit.KG));
        applePieIngredients.add(createIngredient( YEAST, 0.001, Unit.KG));
        applePieIngredients.add(createIngredient(SUGAR, 0.01, Unit.KG));
        applePieIngredients.add(createIngredient(MELTED_BUTTER, 0.01, Unit.LITER));
        applePieIngredients.add(createIngredient(VEGETABLE_OIL, 0.001, Unit.LITER));
        applePieIngredients.add(createIngredient( WATER, 0.005, Unit.LITER));
        
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
        assertThat(result).hasSize(1);
        assertThat(result).element(0).returns(APPLE, from(Ingredient::getName));
    }

    @Test
    public void testGetIngredientsHeavierThan1Kg() {
        //when
        List<Ingredient> result = ingredientRepository.findAllHeavierThan1Kg();

        //then
        assertThat(result).hasSize(2);
        assertThat(result).element(0).returns(APPLE, from(Ingredient::getName));
        assertThat(result).element(1).returns(FLOUR, from(Ingredient::getName));
    }
}
