package com.practice.cooking.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.model.Chef;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChefMapperTest {
    
    private static final Long CHEF_ID = 5L;
    private static final String CHEF_NAME = "Haily";
        
    @Autowired
    private ChefDtoToEntityMapper dtoToEntityMapper;
    
    @Autowired
    private ChefEntityToDtoMapper entityToDtoMapper;
    
    @Test
    public void testChefToDtoConverter() {
        Chef chef = new Chef(CHEF_ID, CHEF_NAME);
        ChefDto chefDto = entityToDtoMapper.entityToDto(chef);

        assertAll(
            "ChefDto mapped object",
            () -> assertEquals(CHEF_ID, chefDto.getId()),
            () -> assertEquals(CHEF_NAME, chefDto.getName())
        );
    }
    
    @Test
    public void testChefDtoToEntityConverter() {
        ChefDto chefDto = new ChefDto(CHEF_ID, CHEF_NAME);
        Chef chef = dtoToEntityMapper.dtoToEntity(chefDto);

        assertAll(
            "Chef mapped object",
            () -> assertEquals(CHEF_ID, chef.getId()),
            () -> assertEquals(CHEF_NAME, chef.getName())
        );
    }
    
}
