package com.practice.cooking.service;

import java.util.List;
import java.util.Optional;

import static com.practice.cooking.utils.TestUtils.createIngredientWithId;
import static com.practice.cooking.utils.TestUtils.createSimpleIngredient;
import static com.practice.cooking.utils.TestUtils.getApplePieIngredients;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Unit;
import com.practice.cooking.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = IngredientServiceTest.IngredientServiceTestConfig.class)
public class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    @Test
    public void testGetAllIngredients() {
        when(ingredientRepository.findAll()).thenReturn(getApplePieIngredients());

        List<Ingredient> ingredients = ingredientService.getAll();

        checkInitialIngredientList(ingredients);
    }

    private void checkInitialIngredientList(List<Ingredient> ingredients) {
        assertEquals(ingredients.size(), 8);
        assertAll("List of ingredients",
            () -> assertEquals("Apple", ingredients.get(0).getName()),
            () -> assertEquals("Flour", ingredients.get(1).getName()),
            () -> assertEquals("Cinnamon", ingredients.get(2).getName()),
            () -> assertEquals("Yeast", ingredients.get(3).getName()),
            () -> assertEquals("Sugar", ingredients.get(4).getName()),
            () -> assertEquals("Melted Butter", ingredients.get(5).getName()),
            () -> assertEquals("Vegetable oil", ingredients.get(6).getName()),
            () -> assertEquals("Water", ingredients.get(7).getName())
        );
    }

    @Test
    public void testSaveAndGetIngredientById() {
        Ingredient newAddedIngredient = createIngredientWithId(6L, "Paprika", 0.001, Unit.KG);
        when(ingredientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newAddedIngredient));

        ingredientRepository.save(newAddedIngredient);
        Ingredient retrievedIngredient = ingredientService.getById(6L);

        //here we verify if the record was saved in the DB, 
        //and also if the getById method works
        assertEquals(retrievedIngredient, newAddedIngredient);
    }

    @Test
    public void testDeleteIngredient() {
        Ingredient ingredient = createSimpleIngredient("Test ingredient", 1, Unit.PIECE);
        ingredient.setId(1L);

        when(ingredientRepository.findById(1L)).thenReturn(Optional.ofNullable(ingredient));

        ingredientService.delete(ingredient.getId());
        Mockito.verify(ingredientRepository, Mockito.atLeastOnce()).delete(ingredient);
    }

    @Configuration
    public static class IngredientServiceTestConfig {

        @Bean
        public IngredientRepository ingredientRepository() {
            return Mockito.mock(IngredientRepository.class);
        }

    }
}
