package com.practice.cooking.mapper;

import java.util.Set;
import java.util.TreeSet;

import static com.practice.cooking.utils.TestUtils.getRisottoIngredients;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecipeMapperTest {

    public static final Long       RECIPE_ID           = 2L;
    public static final String     RECIPE_NAME         = "Risotto";
    public static final Difficulty RECIPE_DIFFICULTY   = Difficulty.MEDIUM;
    public static final Integer    RECIPE_COOKING_TIME = 1;
    public static final RecipeType RECIPE_TYPE         = RecipeType.SIDE;

    @Autowired
    private RecipeEntityToDtoMapper entityToDtoMapper;

    @Autowired
    private RecipeDtoToEntityMapper dtoToEntityMapper;

    @Autowired
    private IngredientEntityToDtoMapper ingredientEntityToDtoMapper;

    @Test
    public void testRecipeToDtoConversion() {
        Recipe recipe = new Recipe(RECIPE_ID, RECIPE_NAME, RECIPE_DIFFICULTY, getRisottoIngredients(), RECIPE_COOKING_TIME, RECIPE_TYPE);
        RecipeDto recipeDto = entityToDtoMapper.entityToDto(recipe);

        assertAll(
            "RecipeDto converted object",
            () -> assertEquals(RECIPE_ID, recipeDto.getId()),
            () -> assertEquals(RECIPE_NAME, recipeDto.getName()),
            () -> assertEquals(RECIPE_DIFFICULTY, recipeDto.getDifficulty()),
            () -> assertEquals(RECIPE_COOKING_TIME, recipeDto.getCookingTime()),
            () -> assertEquals(RECIPE_TYPE, recipeDto.getRecipeType()),
            () -> assertEquals("Rice", recipeDto.getIngredients().stream().sorted().findFirst().get().getName())
        );
    }

    @Test
    public void testRecipeDtoRoEntityConversion() {
        Set<IngredientDto> ingredientDtoList = new TreeSet<>();
        for (Ingredient ingredient : getRisottoIngredients()) {
            ingredientDtoList.add(ingredientEntityToDtoMapper.entityToDto(ingredient));
        }
        
        RecipeDto recipeDto = new RecipeDto(RECIPE_ID, RECIPE_NAME, RECIPE_DIFFICULTY, ingredientDtoList, RECIPE_COOKING_TIME, RECIPE_TYPE);
        Recipe recipe = dtoToEntityMapper.dtoToEntity(recipeDto);

        assertAll(
            "Recipe converted object",
            () -> assertEquals(RECIPE_ID, recipe.getId()),
            () -> assertEquals(RECIPE_NAME, recipe.getName()),
            () -> assertEquals(RECIPE_DIFFICULTY, recipe.getDifficulty()),
            () -> assertEquals(RECIPE_COOKING_TIME, recipe.getCookingTime()),
            () -> assertEquals(RECIPE_TYPE, recipe.getRecipeType()),
            () -> assertEquals("Rice", recipe.getIngredients().stream().sorted().findFirst().get().getName())
        );
    }

}