package com.practice.cooking.mapper;

import java.util.stream.Collectors;

import static com.practice.cooking.utils.TestUtils.createDish;
import static com.practice.cooking.utils.TestUtils.getRecipeList;
import static com.practice.cooking.utils.TestUtils.getRisottoIngredients;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.dto.DishDto;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.RecipeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DishMapperTest {

    public static final Long   DISH_ID   = 33L;
    public static final String DISH_NAME = "Risotto";

    @Autowired
    private DishDtoToEntityMapper dtoToEntityMapper;

    @Autowired
    private DishEntityToDtoMapper entityToDtoMapper;

    @Autowired
    private IngredientEntityToDtoMapper ingredientEntityToDtoMapper;

    @Test
    public void testDishToDtoConversion() {
        Dish dish = createDish(DISH_NAME, getRecipeList().get(1));
        dish.getRecipe().setIngredients(getRisottoIngredients());
        dish.setId(DISH_ID);
        DishDto dishDto = entityToDtoMapper.entityToDto(dish);
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
        DishDto dishDto = entityToDtoMapper.entityToDto(createDish(DISH_NAME, getRecipeList().get(1)));
        dishDto.getRecipe().setIngredients(
            getRisottoIngredients().stream()
                .map(ingredient -> ingredientEntityToDtoMapper.entityToDto(ingredient))
                .collect(Collectors.toSet())
        );
        dishDto.setId(DISH_ID);
        Dish dish = dtoToEntityMapper.dtoToEntity(dishDto);
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
