package com.practice.cooking.scheduler;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.service.RestaurantService;
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
@ContextConfiguration(classes = RestaurantSchedulerTest.RestaurantSchedulerTestConfig.class)
public class RestaurantSchedulerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantJobScheduler restaurantJobScheduler;

    @Test
    public void verifyIfServiceMethodIsCalledInsideDisplayRestaurantsMethod() {
        restaurantJobScheduler.displayRestaurants();
        verify(restaurantService, atLeast(1)).getAll();
    }

    @Test
    public void verifyIfServiceMethodIsCalledInsideInsideUpgradeRestaurantByStarsNumberMethods() {
        when(restaurantService.getAll()).thenReturn(TestUtils.getRestaurantList());
        restaurantJobScheduler.upgradeRestaurantByStarsNumber();
        verify(restaurantService, atLeast(1)).getAll();
        verify(restaurantService, times(1)).update(anyLong(), any(Restaurant.class));
    }

    @Configuration
    public static class RestaurantSchedulerTestConfig {

        @Bean
        public RestaurantService restaurantService() {
            return Mockito.mock(RestaurantService.class);
        }

        @Bean
        public RestaurantJobScheduler restaurantJobScheduler() {
            return new RestaurantJobScheduler(restaurantService());
        }
    }
}
