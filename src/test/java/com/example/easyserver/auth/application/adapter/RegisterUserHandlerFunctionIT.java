package com.example.easyserver.auth.application.adapter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = "/sql/auth/register_user/main.sql")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class RegisterUserHandlerFunctionIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void registerUser_RequestIsValid_ReturnsResponseWithStatusNoContent() throws Exception {

        // given
        final var requestBuilder = post("/api/register")
                .param("name", "User")
                .param("password", "password");

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpect(status().isNoContent());
    }
}
