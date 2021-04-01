package com.practice.cooking.scheduler;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.service.DishService;
import com.practice.cooking.service.RecipeService;
import com.practice.cooking.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = RecipeSchedulerTest.RecipeSchedulerTestConfig.class)
public class RecipeSchedulerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeJobScheduler recipeJobScheduler;

    @Test
    public void verifyIfServiceMethodIsCalledInsideDisplayRecipesMethod() {
        recipeJobScheduler.displayRecipes();
        verify(recipeService, atLeast(1)).getAll();
    }

    @Test
    public void verifyIfServiceMethodIsCalledInsideInsideUpdateRecipeDifficultyHardLevelMethods() {
        when(recipeService.getAll()).thenReturn(TestUtils.getRecipeList());
        recipeJobScheduler.changeRecipeDifficultyLevelToHardAccordingToTheCookingTime();
        verify(recipeService, atLeast(1)).getAll();
        verify(recipeService, times(2)).update(anyLong(), any(Recipe.class));
    }

    @Test
    public void verifyIfServiceMethodIsCalledInsideInsideUpdateRecipeDifficultyMediumLevelMethods() {
        when(recipeService.getAll()).thenReturn(TestUtils.getRecipeList());
        recipeJobScheduler.changeRecipeDifficultyLevelToMediumAccordingToTheCookingTime();
        verify(recipeService, atLeast(1)).getAll();
        verify(recipeService, times(2)).update(anyLong(), any(Recipe.class));
    }

    @Configuration
    public static class RecipeSchedulerTestConfig {

        @Bean
        public RecipeService recipeService() {
            return Mockito.mock(RecipeService.class);
        }

        @Bean
        public RecipeJobScheduler recipeJobScheduler() {
            return new RecipeJobScheduler(recipeService());
        }
    }
}
