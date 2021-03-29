package com.practice.cooking.conversion;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.model.Chef;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

@SpringBootTest
public class ChefConversionTest {
    
    private static final Long CHEF_ID = 5L;
    private static final String CHEF_NAME = "Haily";
        
    @Autowired
    private ConversionService conversionService;
    
    @Test
    public void testChefToDtoConverter() {
        Chef chef = new Chef(CHEF_ID, CHEF_NAME);
        ChefDto chefDto = conversionService.convert(chef, ChefDto.class);

        assertAll(
            "ChefDto mapped object",
            () -> assertEquals(CHEF_ID, chefDto.getId()),
            () -> assertEquals(CHEF_NAME, chefDto.getName())
        );
    }
    
    @Test
    public void testChefDtoToEntityConverter() {
        ChefDto chefDto = new ChefDto(CHEF_ID, CHEF_NAME);
        Chef chef = conversionService.convert(chefDto, Chef.class);

        assertAll(
            "Chef mapped object",
            () -> assertEquals(CHEF_ID, chef.getId()),
            () -> assertEquals(CHEF_NAME, chef.getName())
        );
    }
    
}
