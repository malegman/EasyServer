package com.example.easyserver.greeting.application.adapter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingHandlerFunctionIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void greeting_ResponseIsValid_ReturnsResponseWithStatusOk() throws Exception {

        // given
        final var requestBuilder = MockMvcRequestBuilders.get("/")
                .param("user", "User");

        // when
        this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello, User!"));
    }
}