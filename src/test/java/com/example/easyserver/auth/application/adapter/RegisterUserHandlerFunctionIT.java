package com.example.easyserver.auth.application.adapter;

import com.example.easyserver.auth.application.adapter.input.spring.http.AuthMediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .contentType(AuthMediaType.EASY_SERVER_AUTH_REGISTER_JSON)
                .content("""
                        {
                            "name": "User",
                            "password": "password"
                        }""");

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpect(status().isNoContent());
    }
}
