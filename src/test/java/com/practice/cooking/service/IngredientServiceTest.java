package com.practice.cooking.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Unit;
import com.practice.cooking.repository.IngredientRepository;
import com.practice.cooking.utils.TestUtils;
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
        when(ingredientRepository.findAll()).thenReturn(TestUtils.getApplePieIngredients().stream().collect(Collectors.toList()));

        List<Ingredient> ingredients = ingredientService.getAll();

        checkInitialIngredientList(ingredients);
    }

    private void checkInitialIngredientList(List<Ingredient> ingredients) {
        assertEquals(ingredients.size(), 7);
        assertAll("List of ingredients",
            () -> assertEquals("Apple", ingredients.get(0).getName()),
            () -> assertEquals("Flour", ingredients.get(1).getName()),
            () -> assertEquals("Cinnamon", ingredients.get(2).getName()),
            () -> assertEquals("Yeast", ingredients.get(3).getName()),
            () -> assertEquals("Sugar", ingredients.get(4).getName()),
            () -> assertEquals("Melted Butter", ingredients.get(5).getName()),
            () -> assertEquals("Vegetable oil", ingredients.get(6).getName())
            );
    }

    @Test
    public void testSaveAndGetIngredientById() {
        Ingredient newAddedIngredient = TestUtils.createIngredient("Paprika", 0.001, Unit.KG);
        newAddedIngredient.setId(6L);
        when(ingredientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newAddedIngredient));

        ingredientRepository.save(newAddedIngredient);
        Ingredient retrievedIngredient = ingredientService.getById(6L);

        //here we verify if the record was saved in the DB, 
        //and also if the getById method works
        assertEquals(retrievedIngredient, newAddedIngredient);
    }

    @Test
    public void testDeleteIngredient() {
        when(ingredientRepository.findAll()).thenReturn(TestUtils.getApplePieIngredients().stream().collect(Collectors.toList()));

        List<Ingredient> ingredients = ingredientService.getAll();
        when(ingredientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(ingredients.get(0)));

        checkInitialIngredientList(ingredients);
        ingredientService.delete(ingredients.get(0).getId());
        Mockito.verify(ingredientRepository, Mockito.atLeastOnce()).delete(ingredients.get(0));
    }

    @Configuration
    public static class IngredientServiceTestConfig {

        @Bean
        public IngredientRepository ingredientRepository() {
            return Mockito.mock(IngredientRepository.class);
        }

    }
}
