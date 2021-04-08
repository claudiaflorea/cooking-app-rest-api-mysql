package com.practice.cooking.controller;

import static com.practice.cooking.utils.TestUtils.createIngredientWithId;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.cooking.dto.IngredientDto;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Unit;
import com.practice.cooking.service.IngredientService;
import com.practice.cooking.utils.TestUtils;
import com.practice.cooking.validator.IngredientDtoValidator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = IngredientController.class)
@RunWith(SpringRunner.class)
public class IngredientControllerTest {

    public static final long   INGREDIENT_ID           = 1L;
    public static final String INGREDIENT_NAME         = "Sugar";
    public static final double INGREDIENT_QUANTITY     = 1;
    public static final Unit   INGREDIENT_UNIT         = Unit.KG;
    public static final String INGREDIENT_NAME_UPDATED = "Honey";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private IngredientService ingredientService;
    
    @MockBean
    private IngredientDtoValidator ingredientDtoValidator;

    IngredientDto ingredient = createIngredientWithId(INGREDIENT_ID, INGREDIENT_NAME, INGREDIENT_QUANTITY, INGREDIENT_UNIT);

    @Test
    public void testGetIngredientByIdWithValidParameters() throws Exception {
        String url = "/api/ingredients/{id}";

        when(ingredientService.getById(INGREDIENT_ID)).thenReturn(ingredient);

        mockMvc.perform(get(url, 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(INGREDIENT_ID))
            .andExpect(jsonPath("$.name").value(INGREDIENT_NAME))
            .andExpect(jsonPath("$.quantity").value(INGREDIENT_QUANTITY))
            .andExpect(jsonPath("$.unit").value(INGREDIENT_UNIT.toString()));

    }

    @Test
    public void addIngredientTest() throws Exception {
        String url = "/api/ingredients";
        when(ingredientDtoValidator.supports(IngredientDto.class)).thenReturn(true);
        when(ingredientService.add(ingredient)).thenAnswer(
            (Answer<IngredientDto>) invocation -> invocation.getArgument(0)
        );

        mockMvc.perform(post(url)
            .content(om.writeValueAsString(ingredient))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated());
    }

    @Test
    public void testGetIngredientByIdWithInvalidParameters() throws Exception {
        String url = "/api/ingredients/{id}";
        when(ingredientService.getById(INGREDIENT_ID)).thenReturn(ingredient);

        mockMvc.perform(get(url, "/??"))
            .andExpect(status().is5xxServerError());
    }

    @Test
    public void testGetWithInvalidPath() throws Exception {
        String url = "/api";

        mockMvc.perform(get(url, "/??"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateIngredientTest() throws Exception {
        String url = "/api/ingredients/{id}";
        ingredient.setName(INGREDIENT_NAME_UPDATED);
        when(ingredientDtoValidator.supports(IngredientDto.class)).thenReturn(true);
        
        mockMvc.perform(put(url, INGREDIENT_ID)
            .content(om.writeValueAsString(ingredient))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());
    }

    @Test
    public void deleteIngredientTest() throws Exception {
        String url = "/api/ingredients/{id}";

        mockMvc.perform(delete(url, INGREDIENT_ID))
            .andExpect(status().isAccepted());
    }

}


