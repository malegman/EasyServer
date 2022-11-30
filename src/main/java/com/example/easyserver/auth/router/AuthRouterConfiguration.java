package com.example.easyserver.auth.router;

import com.example.easyserver.auth.application.adapter.spring.http.RegisterUserHandlerFunction;
import com.example.easyserver.auth.application.adapter.spring.jdbc.SpringJdbcRegisterUserOutputPortAdapter;
import com.example.easyserver.auth.application.usecase.RegisterUserUseCase;
import com.example.easyserver.validation.SelfValidatingValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import javax.sql.DataSource;

@Configuration
public class AuthRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> registerUserRouterFunction(
            final PlatformTransactionManager platformTransactionManager,
            final DataSource dataSource) {
        return RouterFunctions.route()
                .POST("/api/register",
                        new RegisterUserHandlerFunction(
                                new TransactionTemplate(platformTransactionManager) {{
                                    this.setName("register_user");
                                }},
                                new RegisterUserUseCase(
                                        SelfValidatingValidator.INSTANCE,
                                        new SpringJdbcRegisterUserOutputPortAdapter(dataSource))))
                .build();
    }
}
