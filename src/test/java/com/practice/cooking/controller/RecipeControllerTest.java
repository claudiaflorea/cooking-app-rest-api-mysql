package com.practice.cooking.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = RecipeController.class)
@RunWith(SpringRunner.class)
public class RecipeControllerTest {

    public static final long       RECIPE_ID           = 2L;
    public static final String     RECIPE_NAME         = "Burger";
    public static final Difficulty RECIPE_DIFFICULTY   = Difficulty.MEDIUM;
    public static final Integer    RECIPE_COOKING_TIME = 1;
    public static final RecipeType RECIPE_TYPE         = RecipeType.MAIN_COURSE;
    public static final String     RECIPE_NAME_UPDATED = "Cheeseburger";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private RecipeService recipeService;

    Recipe recipe = new Recipe(RECIPE_ID, RECIPE_NAME, Difficulty.MEDIUM, null, 1, RecipeType.MAIN_COURSE);

    @Test
    public void testGetRecipeByIdWithValidParameters() throws Exception {
        String url = "/api/recipes/{id}";

        when(recipeService.getById(RECIPE_ID)).thenReturn(recipe);

        mockMvc.perform(get(url, 2))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(RECIPE_ID))
            .andExpect(jsonPath("$.name").value(RECIPE_NAME))
            .andExpect(jsonPath("$.difficulty").value(RECIPE_DIFFICULTY.toString()))
            .andExpect(jsonPath("$.cookingTime").value(RECIPE_COOKING_TIME))
            .andExpect(jsonPath("$.recipeType").value(RECIPE_TYPE.toString()));
    }

    @Test
    public void testGetRecipeByIdWithInvalidParameters() throws Exception {
        String url = "/api/recipes/{id}";
        when(recipeService.getById(RECIPE_ID)).thenReturn(recipe);

        mockMvc.perform(get(url, "/??"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void addRecipeTest() throws Exception {
        String url = "/api/recipes/";

        when(recipeService.add(recipe)).thenAnswer(
            (Answer<Recipe>) invocation -> invocation.getArgument(0)
        );

        mockMvc.perform(post(url)
            .content(om.writeValueAsString(recipe))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    public void updateRecipeTest() throws Exception {
        String url = "/api/recipes/{id}";
        recipe.setName(RECIPE_NAME_UPDATED);

        mockMvc.perform(put(url, RECIPE_ID)
            .content(om.writeValueAsString(recipe))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void deleteRecipeTest() throws Exception {
        String url = "/api/recipes/{id}";

        mockMvc.perform(delete(url, RECIPE_ID))
            .andExpect(status().isAccepted());
    }

}


