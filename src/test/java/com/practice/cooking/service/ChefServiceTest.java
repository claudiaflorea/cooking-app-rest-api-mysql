package com.practice.cooking.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.model.Chef;
import com.practice.cooking.repository.ChefRepository;
import com.practice.cooking.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = ChefServiceTest.ChefServiceTestConfig.class)
public class ChefServiceTest {

    @Mock
    private ChefRepository chefRepository;

    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private ChefService chefService;

    @Test
    public void testGetAllChefs() {
        Mockito.when(chefRepository.findAll()).thenReturn(TestUtils.getChefList());

        List<Chef> chefs = chefService.getAll();

        checkInitialChefList(chefs);
    }

    private void checkInitialChefList(List<Chef> chefs) {
        assertEquals(chefs.size(), 5);
        assertAll("List of chefs",
            () -> assertEquals("Eugene", chefs.get(0).getName()),
            () -> assertEquals("Stan", chefs.get(1).getName()),
            () -> assertEquals("Thor", chefs.get(2).getName()),
            () -> assertEquals("Loki", chefs.get(3).getName()),
            () -> assertEquals("Vader", chefs.get(4).getName())
        );
    }

    @Test
    public void testSaveAndGetChefById() {
        Chef newAddedChef = new Chef(6L, "Bernie");
        Mockito.when(chefRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newAddedChef));

        chefRepository.save(newAddedChef);
        Chef retrievedChef = chefService.getById(6L);

        //here we verify if the record was saved in the DB, 
        //and also if the getById method works
        assertEquals(retrievedChef, newAddedChef);
    }

    @Test
    public void testDeleteChef() {
        Mockito.when(chefRepository.findAll()).thenReturn(TestUtils.getChefList());

        List<Chef> chefs = chefService.getAll();
        Mockito.when(chefRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(chefs.get(0)));

        checkInitialChefList(chefs);
        chefService.delete(chefs.get(0).getId());
        Mockito.verify(chefRepository, Mockito.atLeastOnce()).delete(chefs.get(0));
    }

    @Configuration
    public static class ChefServiceTestConfig {

        @Bean
        public ChefRepository chefRepository() {
            return Mockito.mock(ChefRepository.class);
        }

        @Bean
        public MongoOperations mongoOperations() {
            return Mockito.mock(MongoOperations.class);
        }

    }
}
