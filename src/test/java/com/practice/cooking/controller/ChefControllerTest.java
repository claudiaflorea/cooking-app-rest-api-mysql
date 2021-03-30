package com.practice.cooking.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.cooking.model.Chef;
import com.practice.cooking.service.ChefService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = ChefController.class)
@RunWith(SpringRunner.class)
public class ChefControllerTest {

    public static final long   CHEF_ID           = 14L;
    public static final String CHEF_NAME         = "Luigi";
    public static final String CHEF_NAME_UPDATED = "Sam";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private ChefService chefService;

    Chef chef = new Chef(CHEF_ID, CHEF_NAME);

    @Test
    public void testGetChefByIdWithValidParameters() throws Exception {
        String url = "/api/chefs/{id}";
        when(chefService.getById(CHEF_ID)).thenReturn(chef);

        mockMvc.perform(get(url, 14))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(CHEF_ID))
            .andExpect(jsonPath("$.name").value(CHEF_NAME));
    }

    @Test
    public void testGetChefByIdWithInvalidPath() throws Exception {
        String url = "/api";

        mockMvc.perform(get(url, "/??"))
            .andExpect(status().is5xxServerError());
    }

    @Test
    public void addChefTest() throws Exception {
        String url = "/api/chefs";

        when(chefService.add(chef)).thenAnswer(
            (Answer<Chef>) invocation -> invocation.getArgument(0)
        );

        mockMvc.perform(post(url)
            .content(om.writeValueAsString(chef))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated());
    }

    @Test
    public void updateChefTest() throws Exception {
        String url = "/api/chefs/{id}";
        Chef chef = new Chef();
        chef.setName(CHEF_NAME_UPDATED);

        mockMvc.perform(put(url, CHEF_ID)
            .content(om.writeValueAsString(chef))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void deleteChefTest() throws Exception {
        String url = "/api/chefs/{id}";

        mockMvc.perform(delete(url, CHEF_ID))
            .andExpect(status().isAccepted());
    }

}


