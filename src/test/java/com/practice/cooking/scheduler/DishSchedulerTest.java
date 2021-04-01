package com.practice.cooking.scheduler;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.practice.cooking.model.Dish;
import com.practice.cooking.service.DishService;
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
@ContextConfiguration(classes = DishSchedulerTest.DishSchedulerTestConfig.class)
public class DishSchedulerTest {

    @Mock
    private DishService dishService;

    @InjectMocks
    private DishJobScheduler dishJobScheduler;

    @Test
    public void verifyIfServiceMethodIsCalledInsideDisplayDishesMethod() {
        dishJobScheduler.displayDishes();
        verify(dishService, atLeast(1)).getAll();
    }

    @Test
    public void verifyIfServiceMethodIsCalledInsideInsideUpdateDishNameByRecipeName() {
        when(dishService.getAll()).thenReturn(TestUtils.getDishList());
        dishJobScheduler.updateDishNameByRecipeName();
        verify(dishService, atLeast(1)).getAll();
        verify(dishService, times(1)).update(anyLong(), any(Dish.class));
    }

    @Configuration
    public static class DishSchedulerTestConfig {

        @Bean
        public DishService dishService() {
            return Mockito.mock(DishService.class);
        }

        @Bean
        public DishJobScheduler dishJobScheduler() {
            return new DishJobScheduler(dishService());
        }
    }
}
