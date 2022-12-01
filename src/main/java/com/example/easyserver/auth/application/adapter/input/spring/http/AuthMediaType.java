package com.example.easyserver.auth.application.adapter.input.spring.http;

import org.springframework.http.MediaType;

public class AuthMediaType {

    public static final String EASY_SERVER_AUTH_REGISTER_JSON_VALUE =
            "easy-server.api.register/json";
    public static final MediaType EASY_SERVER_AUTH_REGISTER_JSON =
            MediaType.parseMediaType(EASY_SERVER_AUTH_REGISTER_JSON_VALUE);
}
