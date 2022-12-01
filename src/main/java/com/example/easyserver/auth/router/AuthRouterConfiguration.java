package com.example.easyserver.auth.router;

import com.example.easyserver.auth.application.adapter.input.spring.http.AuthMediaType;
import com.example.easyserver.auth.application.adapter.input.spring.mvc.RegisterUserHandlerFunction;
import com.example.easyserver.auth.application.adapter.output.spring.jdbc.SpringJdbcRegisterUserOutputPortAdapter;
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

import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@Configuration
public class AuthRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> registerUserRouterFunction(
            final PlatformTransactionManager platformTransactionManager,
            final DataSource dataSource) {
        return RouterFunctions.route()
                .POST("/api/register",
                        contentType(AuthMediaType.EASY_SERVER_AUTH_REGISTER_JSON),
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
