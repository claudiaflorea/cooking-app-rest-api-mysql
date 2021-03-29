package com.practice.cooking.conversion;

import java.util.ArrayList;
import java.util.List;

import static com.practice.cooking.utils.TestUtils.getRisottoIngredients;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.converter.RecipeEntityToDtoConverter;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

@SpringBootTest
public class RecipeConversionTest {

    public static final Long       RECIPE_ID           = 2L;
    public static final String     RECIPE_NAME         = "Risotto";
    public static final Difficulty RECIPE_DIFFICULTY   = Difficulty.MEDIUM;
    public static final Integer    RECIPE_COOKING_TIME = 1;
    public static final RecipeType RECIPE_TYPE         = RecipeType.SIDE;

    @Autowired
    private RecipeEntityToDtoConverter recipeConverter;
    
    @Autowired
    private ConversionService conversionService;

    @Test
    public void testRecipeToDtoConversion() {
        Recipe recipe = new Recipe(RECIPE_ID, RECIPE_NAME, RECIPE_DIFFICULTY, getRisottoIngredients(), RECIPE_COOKING_TIME, RECIPE_TYPE);
        RecipeDto recipeDto = recipeConverter.convert(recipe);

        assertAll(
            "RecipeDto converted object",
            () -> assertEquals(RECIPE_ID, recipeDto.getId()),
            () -> assertEquals(RECIPE_NAME, recipeDto.getName()),
            () -> assertEquals(RECIPE_DIFFICULTY, recipeDto.getDifficulty()),
            () -> assertEquals(RECIPE_COOKING_TIME, recipeDto.getCookingTime()),
            () -> assertEquals(RECIPE_TYPE, recipeDto.getRecipeType()),
            () -> assertEquals("Rice", recipeDto.getIngredients().get(0).getName())
        );
    }

    @Test
    public void testRecipeDtoRoEntityConversion() {
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        for (Ingredient ingredient : getRisottoIngredients()) {
            ingredientDtoList.add(conversionService.convert(ingredient, IngredientDto.class));
        }
        
        RecipeDto recipeDto = new RecipeDto(RECIPE_ID, RECIPE_NAME, RECIPE_DIFFICULTY, ingredientDtoList, RECIPE_COOKING_TIME, RECIPE_TYPE);
        Recipe recipe = conversionService.convert(recipeDto, Recipe.class);

        assertAll(
            "Recipe converted object",
            () -> assertEquals(RECIPE_ID, recipe.getId()),
            () -> assertEquals(RECIPE_NAME, recipe.getName()),
            () -> assertEquals(RECIPE_DIFFICULTY, recipe.getDifficulty()),
            () -> assertEquals(RECIPE_COOKING_TIME, recipe.getCookingTime()),
            () -> assertEquals(RECIPE_TYPE, recipe.getRecipeType()),
            () -> assertEquals("Rice", recipe.getIngredients().get(0).getName())
        );
    }

}
