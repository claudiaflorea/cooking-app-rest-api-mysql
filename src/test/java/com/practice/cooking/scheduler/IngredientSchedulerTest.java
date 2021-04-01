package com.practice.cooking.scheduler;


import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import com.practice.cooking.service.IngredientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = IngredientSchedulerTest.IngredientSchedulerTestConfig.class)
public class IngredientSchedulerTest {

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private IngredientJobScheduler ingredientJobScheduler;

    @Test
    public void verifyIfServiceMethodIsCalledInsideDisplayIngredientsMethod() {
        ingredientJobScheduler.displayIngredients();
        verify(ingredientService, atLeast(1)).getAll();
    }

    @Configuration
    public static class IngredientSchedulerTestConfig {

        @Bean
        public IngredientService ingredientService() {
            return Mockito.mock(IngredientService.class);
        }

        @Bean
        public IngredientJobScheduler ingredientJobScheduler() {
            return new IngredientJobScheduler(ingredientService());
        }
    }
}
