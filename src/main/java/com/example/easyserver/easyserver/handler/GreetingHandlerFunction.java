package com.example.easyserver.easyserver.handler;

import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

public final class GreetingHandlerFunction implements HandlerFunction<ServerResponse> {

    @Override
    public ServerResponse handle(ServerRequest request) throws Exception {
        return ServerResponse
                .ok()
                .body("Hello, user! You are in 'Easy Server'!");
    }
}
