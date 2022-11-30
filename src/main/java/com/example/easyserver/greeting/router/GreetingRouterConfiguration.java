package com.example.easyserver.greeting.router;

import com.example.easyserver.greeting.application.adapter.http.GreetingHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GreetingRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> greetingRouterFunction(
            final PlatformTransactionManager platformTransactionManager) {
        return RouterFunctions.route()
                .GET("/",
                        new GreetingHandlerFunction(
                                new TransactionTemplate(platformTransactionManager) {{
                                    this.setName("greeting");
                                    this.setReadOnly(true);
                                }}))
                .build();
    }
}
