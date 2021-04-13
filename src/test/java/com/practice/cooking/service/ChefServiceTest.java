package com.practice.cooking.service;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.mapper.ChefDtoToEntityMapper;
import com.practice.cooking.mapper.ChefEntityToDtoMapper;
import com.practice.cooking.model.Chef;
import com.practice.cooking.publisher.Publisher;
import com.practice.cooking.repository.ChefRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChefServiceTest {

    private static final String EUGENE = "Eugene";
    private static final String STAN   = "Stan";
    private static final String BERNIE = "Bernie";
    private static final String PETER  = "Peter";

    @Mock
    private ChefRepository chefRepository;

    @Mock
    private Publisher publisher;

    @Mock
    private ChefEntityToDtoMapper entityToDtoMapper;

    @Mock
    private ChefDtoToEntityMapper dtoToEntityMapper;

    @InjectMocks
    private ChefService chefService;

    @Test
    public void testGetAllChefs() {
        Chef chef1 = new Chef(1L, EUGENE);
        Chef chef2 = new Chef(2L, STAN);
        when(chefRepository.findAll()).thenReturn(asList(chef1, chef2));
        when(entityToDtoMapper.entityToDto(chef1)).thenReturn(new ChefDto(1L, EUGENE));
        when(entityToDtoMapper.entityToDto(chef2)).thenReturn(new ChefDto(2L, STAN));

        List<ChefDto> chefs = chefService.getAll();

        checkInitialChefList(chefs);
    }

    private void checkInitialChefList(List<ChefDto> chefs) {
        assertEquals(chefs.size(), 2);
        assertAll("List of chefs",
            () -> assertEquals(EUGENE, chefs.get(0).getName()),
            () -> assertEquals(STAN, chefs.get(1).getName())
        );
    }

    @Test
    public void testSaveAndGetChefById() {
        Chef newAddedChef = new Chef(6L, BERNIE);
        when(chefRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newAddedChef));
        when(entityToDtoMapper.entityToDto(newAddedChef)).thenReturn(new ChefDto(6L, BERNIE));


        chefRepository.save(newAddedChef);
        ChefDto retrievedChef = chefService.getById(6L);

        //here we verify if the record was saved in the DB, 
        //and also if the getById method works
        assertEquals(retrievedChef.getName(), newAddedChef.getName());
    }

    @Test
    public void testDeleteChef() throws JsonProcessingException {
        Chef chef = new Chef();
        chef.setId(10L);
        chef.setName(PETER);

        ChefDto chefDto = new ChefDto();
        chefDto.setId(10L);
        chefDto.setName(PETER);

        when(dtoToEntityMapper.dtoToEntity(chefDto)).thenReturn(chef);
        when(entityToDtoMapper.entityToDto(chef)).thenReturn(chefDto);
        when(chefRepository.findById(10L)).thenReturn(Optional.of(chef));

        chefService.delete(chef.getId());
        Mockito.verify(chefRepository, Mockito.atLeastOnce()).delete(chef);
    }

}
