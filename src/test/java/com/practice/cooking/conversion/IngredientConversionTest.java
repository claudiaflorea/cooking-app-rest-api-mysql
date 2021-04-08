package com.practice.cooking.conversion;

import static com.practice.cooking.utils.TestUtils.createIngredientWithId;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.converter.IngredientEntityToDtoConverter;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Unit;
import com.practice.cooking.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

@SpringBootTest
public class IngredientConversionTest {

    public static final Long   INGREDIENT_ID       = 12L;
    public static final String INGREDIENT_NAME     = "Cinnamon";
    public static final double INGREDIENT_QUANTITY = 0.001;
    public static final Unit   INGREDIENT_UNIT     = Unit.KG;

    @Autowired
    private ConversionService conversionService;

    @Test
    public void testDishToDtoConversion() {
        IngredientDto ingredient = createIngredientWithId(INGREDIENT_ID, INGREDIENT_NAME, INGREDIENT_QUANTITY, INGREDIENT_UNIT);
        IngredientDto ingredientDto = conversionService.convert(ingredient, IngredientDto.class);

        assertAll("IngredientDto mapped object",
            () -> assertEquals(INGREDIENT_ID, ingredientDto.getId()),
            () -> assertEquals(INGREDIENT_NAME, ingredientDto.getName()),
            () -> assertEquals(INGREDIENT_QUANTITY, ingredientDto.getQuantity()),
            () -> assertEquals(INGREDIENT_UNIT, ingredientDto.getUnit())
        );
    }

    @Test
    public void testDishDtoToEntityConversion() {
        IngredientDto ingredientDto = new IngredientDto(INGREDIENT_ID, INGREDIENT_NAME, INGREDIENT_QUANTITY, INGREDIENT_UNIT);
        Ingredient ingredient = conversionService.convert(ingredientDto, Ingredient.class);

        assertAll("Ingredient mapped object",
            () -> assertEquals(INGREDIENT_ID, ingredient.getId()),
            () -> assertEquals(INGREDIENT_NAME, ingredient.getName()),
            () -> assertEquals(INGREDIENT_QUANTITY, ingredient.getQuantity()),
            () -> assertEquals(INGREDIENT_UNIT, ingredient.getUnit())
        );
    }
}
