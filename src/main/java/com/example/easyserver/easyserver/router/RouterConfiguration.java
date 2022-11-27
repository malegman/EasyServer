package com.example.easyserver.easyserver.router;

import com.example.easyserver.greeting.application.adapter.GreetingHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> greetingRouterFunction(
            final PlatformTransactionManager platformTransactionManager) {
        return RouterFunctions.route(
                RequestPredicates.GET("/"),
                new GreetingHandlerFunction(
                        new TransactionTemplate(platformTransactionManager)));
    }
}
