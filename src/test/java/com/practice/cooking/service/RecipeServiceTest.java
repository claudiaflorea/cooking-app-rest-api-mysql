package com.practice.cooking.service;

import java.util.List;
import java.util.Optional;

import static com.practice.cooking.utils.TestUtils.createRecipe;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

@SpringBootTest
public class RecipeServiceTest {

    private static final String APPLE_PIE = "Apple pie";
    private static final String RISOTTO   = "Risotto";
    private static final String GUACAMOLE = "Guacamole";

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    public void testGetAllRecipes() {
        Recipe applePie = new Recipe(1L, APPLE_PIE, Difficulty.EASY, null, 4, RecipeType.DESSERT);
        Recipe risotto = new Recipe(2L, RISOTTO, Difficulty.MEDIUM, null, 1, RecipeType.SIDE);
        Recipe guacamole = new Recipe(3L, GUACAMOLE, Difficulty.EASY, null, 1, RecipeType.SIDE);

        when(recipeRepository.findAll()).thenReturn(asList(applePie, risotto, guacamole));
        when(conversionService.convert(applePie, RecipeDto.class)).thenReturn(new RecipeDto(1L, APPLE_PIE, Difficulty.EASY, null, 4, RecipeType.DESSERT));
        when(conversionService.convert(risotto, RecipeDto.class)).thenReturn(new RecipeDto(2L, RISOTTO, Difficulty.MEDIUM, null, 1, RecipeType.SIDE));
        when(conversionService.convert(guacamole, RecipeDto.class)).thenReturn(new RecipeDto(3L, GUACAMOLE, Difficulty.EASY, null, 1, RecipeType.SIDE));

        List<RecipeDto> recipes = recipeService.getAll();

        checkInitialRecipesList(recipes);
    }

    private void checkInitialRecipesList(List<RecipeDto> recipes) {
        assertEquals(recipes.size(), 3);
        assertAll("List of recipes",
            () -> assertEquals(APPLE_PIE, recipes.get(0).getName()),
            () -> assertEquals(RISOTTO, recipes.get(1).getName()),
            () -> assertEquals(GUACAMOLE, recipes.get(2).getName())
        );
    }

    @Test
    public void testSaveAndGetRecipeById() {
        Recipe newAddedRecipe = new Recipe(6L, RISOTTO, Difficulty.MEDIUM, null, 1, RecipeType.SIDE);

        when(conversionService.convert(newAddedRecipe, RecipeDto.class)).thenReturn(new RecipeDto(6L, RISOTTO, Difficulty.MEDIUM, null, 1, RecipeType.SIDE));
        when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newAddedRecipe));

        recipeRepository.save(newAddedRecipe);
        RecipeDto retrievedRecipe = recipeService.getById(6L);

        //here we verify if the record was saved in the DB, 
        //and also if the getById method works
        assertEquals(retrievedRecipe.getName(), newAddedRecipe.getName());
        assertEquals(retrievedRecipe.getDifficulty(), newAddedRecipe.getDifficulty());
        assertEquals(retrievedRecipe.getRecipeType(), newAddedRecipe.getRecipeType());
    }

    @Test
    public void testDeleteRecipe() {
        Recipe recipe = createRecipe(GUACAMOLE, Difficulty.EASY, 1, RecipeType.SIDE);
        recipe.setId(1L);
        RecipeDto recipeDto = new RecipeDto(1L, GUACAMOLE, Difficulty.EASY, null, 1, RecipeType.SIDE);

        when(conversionService.convert(recipe, RecipeDto.class)).thenReturn(recipeDto);
        when(conversionService.convert(recipeDto, Recipe.class)).thenReturn(recipe);

        when(recipeRepository.findById(1L)).thenReturn(Optional.ofNullable(recipe));

        recipeService.delete(recipe.getId());
        Mockito.verify(recipeRepository, Mockito.atLeastOnce()).delete(recipe);
    }

}
