package com.practice.cooking.service;

import java.util.List;
import java.util.Optional;

import static com.practice.cooking.utils.TestUtils.createSimpleIngredient;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.mapper.IngredientDtoToEntityMapper;
import com.practice.cooking.mapper.IngredientEntityToDtoMapper;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Unit;
import com.practice.cooking.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IngredientServiceTest {

    private static final String APPLE    = "Apple";
    private static final String CINNAMON = "Cinnamon";
    private static final String DOUGH    = "Dough";
    private static final String PAPRIKA  = "Paprika";

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientEntityToDtoMapper entityToDtoMapper;

    @Mock
    private IngredientDtoToEntityMapper dtoToEntityMapper;

    @InjectMocks
    private IngredientService ingredientService;

    @Test
    public void testGetAllIngredients() {

        Ingredient apple = new Ingredient(1L, APPLE, 2, Unit.KG, null, null);
        Ingredient cinnamon = new Ingredient(2L, CINNAMON, 0.02, Unit.KG, null, null);
        Ingredient dough = new Ingredient(3L, DOUGH, 0.2, Unit.KG, null, null);

        when(ingredientRepository.findAll()).thenReturn(asList(apple, cinnamon, dough));
        when(entityToDtoMapper.entityToDto(apple)).thenReturn(new IngredientDto(1L, APPLE, 2, Unit.KG));
        when(entityToDtoMapper.entityToDto(cinnamon)).thenReturn(new IngredientDto(2L, CINNAMON, 0.02, Unit.KG));
        when(entityToDtoMapper.entityToDto(dough)).thenReturn(new IngredientDto(3L, DOUGH, 0.2, Unit.KG));

        List<IngredientDto> ingredients = ingredientService.getAll();

        checkInitialIngredientList(ingredients);
    }

    private void checkInitialIngredientList(List<IngredientDto> ingredients) {
        assertEquals(ingredients.size(), 3);
        assertAll("List of ingredients",
            () -> assertEquals(APPLE, ingredients.get(0).getName()),
            () -> assertEquals(CINNAMON, ingredients.get(1).getName()),
            () -> assertEquals(DOUGH, ingredients.get(2).getName())
        );
    }

    @Test
    public void testSaveAndGetIngredientById() {
        Ingredient newAddedIngredient = new Ingredient();
        newAddedIngredient.setId(6L);
        newAddedIngredient.setName(PAPRIKA);
        newAddedIngredient.setQuantity(0.01);
        newAddedIngredient.setUnit(Unit.KG);

        when(entityToDtoMapper.entityToDto(newAddedIngredient)).thenReturn(new IngredientDto(6L, PAPRIKA, 0.01, Unit.KG));
        when(ingredientRepository.findById(6L)).thenReturn(Optional.of(newAddedIngredient));

        ingredientRepository.save(newAddedIngredient);
        IngredientDto retrievedIngredient = ingredientService.getById(6L);

        //here we verify if the record was saved in the DB, 
        //and also if the getById method works
        assertEquals(retrievedIngredient.getName(), newAddedIngredient.getName());
        assertEquals(retrievedIngredient.getQuantity(), newAddedIngredient.getQuantity());
        assertEquals(retrievedIngredient.getUnit(), newAddedIngredient.getUnit());
    }

    @Test
    public void testDeleteIngredient() {
        Ingredient ingredient = createSimpleIngredient(APPLE, 1, Unit.PIECE);
        ingredient.setId(1L);

        IngredientDto ingredientDto = new IngredientDto(1L, APPLE, 1, Unit.PIECE);

        when(entityToDtoMapper.entityToDto(ingredient)).thenReturn(ingredientDto);
        when(dtoToEntityMapper.dtoToEntity(ingredientDto)).thenReturn(ingredient);
        when(ingredientRepository.findById(1L)).thenReturn(Optional.ofNullable(ingredient));

        ingredientService.delete(ingredient.getId());
        Mockito.verify(ingredientRepository, Mockito.atLeastOnce()).delete(ingredient);
    }

}
