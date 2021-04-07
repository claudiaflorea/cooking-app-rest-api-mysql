package com.practice.cooking.conversion;

import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.practice.cooking.utils.TestUtils.createDish;
import static com.practice.cooking.utils.TestUtils.getRecipeList;
import static com.practice.cooking.utils.TestUtils.getRisottoIngredients;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.RecipeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

@SpringBootTest
public class DishConversionTest {

    public static final Long   DISH_ID   = 33L;
    public static final String DISH_NAME = "Risotto";

    @Autowired
    private ConversionService conversionService;

    @Test
    public void testDishToDtoConversion() {
        Dish dish = createDish(DISH_NAME, getRecipeList().get(1));
        dish.getRecipe().setIngredients(getRisottoIngredients());
        dish.setId(DISH_ID);
        DishDto dishDto = conversionService.convert(dish, DishDto.class);
        assertAll("DishDto mapped object ",
            () -> assertEquals(DISH_ID, dishDto.getId()),
            () -> assertEquals(DISH_NAME, dishDto.getName()),
            () -> assertEquals(DISH_NAME, dishDto.getRecipe().getName()),
            () -> assertEquals(Difficulty.MEDIUM, dishDto.getRecipe().getDifficulty()),
            () -> assertEquals(1, dishDto.getRecipe().getCookingTime().intValue()),
            () -> assertEquals(RecipeType.SIDE, dishDto.getRecipe().getRecipeType()),
            () -> assertEquals("Rice", dishDto.getRecipe().getIngredients().stream().sorted().collect(Collectors.toList()).get(0).getName())
        );
    }

    @Test
    public void testDishDtoToEntityConversion() {
        DishDto dishDto = conversionService.convert(createDish(DISH_NAME, getRecipeList().get(1)), DishDto.class);
        dishDto.getRecipe().setIngredients(
            getRisottoIngredients().stream()
                .map(ingredient -> conversionService.convert(ingredient, IngredientDto.class))
                .collect(Collectors.toSet())
        );
        dishDto.setId(DISH_ID);
        Dish dish = conversionService.convert(dishDto, Dish.class);
        assertAll("Dish mapped object ",
            () -> assertEquals(DISH_ID, dish.getId()),
            () -> assertEquals(DISH_NAME, dish.getName()),
            () -> assertEquals(DISH_NAME, dish.getRecipe().getName()),
            () -> assertEquals(Difficulty.MEDIUM, dish.getRecipe().getDifficulty()),
            () -> assertEquals(1, dish.getRecipe().getCookingTime().intValue()),
            () -> assertEquals(RecipeType.SIDE, dish.getRecipe().getRecipeType()),
            () -> assertEquals("Rice", dish.getRecipe().getIngredients().stream().sorted().collect(Collectors.toList()).get(0).getName())
        );
    }
}
