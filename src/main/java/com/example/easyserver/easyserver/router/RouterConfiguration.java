package com.example.easyserver.easyserver.router;

import com.example.easyserver.easyserver.handler.GreetingHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> greetingRouterFunction() {
        return RouterFunctions.route(
                RequestPredicates.GET("/"),
                new GreetingHandlerFunction());
    }
}
