package com.practice.cooking.scheduler;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.practice.cooking.model.Chef;
import com.practice.cooking.service.ChefService;
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
@ContextConfiguration(classes = ChefSchedulerTest.ChefSchedulerTestConfig.class)
public class ChefSchedulerTest {

    @Mock
    private ChefService chefService;

    @InjectMocks
    private ChefJobScheduler chefJobScheduler;

    @Test
    public void verifyIfServiceMethodIsCalledInsideDisplayChefsMethod() {
        chefJobScheduler.displayChefs();
        verify(chefService, atLeast(1)).getAll();
    }

    @Test
    public void verifyIfServiceMethodIsCalledInsideInsideUpdateChefNamesMethod() {
        when(chefService.getAll()).thenReturn(TestUtils.getChefList());
        chefJobScheduler.updateChefNames();
        verify(chefService, atLeast(1)).getAll();
        verify(chefService, times(5)).update(anyLong(), any(Chef.class));
    }

    @Configuration
    public static class ChefSchedulerTestConfig {

        @Bean
        public ChefService chefService() {
            return Mockito.mock(ChefService.class);
        }

        @Bean
        public ChefJobScheduler chefJobScheduler() {
            return new ChefJobScheduler(chefService());
        }
    }
}
