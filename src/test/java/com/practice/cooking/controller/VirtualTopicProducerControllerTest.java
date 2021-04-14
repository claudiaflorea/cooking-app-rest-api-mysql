package com.practice.cooking.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(value = VirtualTopicProducerController.class)
@RunWith(SpringRunner.class)
public class VirtualTopicProducerControllerTest {

    @MockBean
    private JmsTemplate jmsTemplate;

    @MockBean
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private static final String message = "Message sent: New message!!!!";

    @Test
    public void testSendMessageViaVirtualQueue() throws Exception {
        String url = "/sendMessageVirtual";

        ResultActions resultActions = mockMvc.perform(get(url))
            .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Assert.assertEquals(message, contentAsString);

    }
}
