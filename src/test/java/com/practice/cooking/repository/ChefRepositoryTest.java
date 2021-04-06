package com.practice.cooking.repository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import com.practice.cooking.model.Chef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChefRepositoryTest {

    private static final String NEW_CHEF    = "Tony";
    private static final String CHEF_EUGENE = "Chef. Eugene";
    private static final String CHEF_STAN   = "Chef. Stan";

    @BeforeEach
    void init() {
        List<Chef> chefs = new ArrayList<>();
        chefs.add(new Chef(1L, CHEF_EUGENE));
        chefs.add(new Chef(2L, CHEF_STAN));
        chefs.add(new Chef(3L, "Thor"));
        chefs.add(new Chef(4L, "Tony"));
        chefs.add(new Chef(5L, "Tony"));

        chefRepository.saveAll(chefs);
    }

    @AfterEach
    void after() {
        chefRepository.deleteAll();
    }

    @Autowired
    private ChefRepository chefRepository;

    @Test
    public void testGetChefByName() {

        //when
        List<Chef> result = chefRepository.findAllByName(NEW_CHEF);

        //then
        assertThat(result).hasSize(2);
        assertThat(result).element(0).returns(NEW_CHEF, from(Chef::getName));
        assertThat(result).element(1).returns(NEW_CHEF, from(Chef::getName));

    }

    @Test
    public void testGetChefsWithPrefixedNames() {

        //when
        List<Chef> result = chefRepository.findAllByNameStartingWithChefPrefix();

        //then
        assertThat(result).hasSize(2);
        assertThat(result).element(0).returns(CHEF_EUGENE, from(Chef::getName));
        assertThat(result).element(1).returns(CHEF_STAN, from(Chef::getName));
    }
}
