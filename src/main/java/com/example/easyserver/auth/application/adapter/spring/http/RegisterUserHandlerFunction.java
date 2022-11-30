package com.example.easyserver.auth.application.adapter.spring.http;

import com.example.easyserver.auth.application.port.input.RegisterUserInputPort;
import com.example.easyserver.auth.application.port.input.RegisterUserInputPort.Result.*;
import com.example.easyserver.common.AbstractHandlerFunction;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Objects;

public final class RegisterUserHandlerFunction extends AbstractHandlerFunction
        implements Processor<ServerResponse> {

    private final RegisterUserInputPort registerUserInputPort;

    public RegisterUserHandlerFunction(
            final TransactionOperations transactionOperations,
            final RegisterUserInputPort registerUserInputPort) {
        super(transactionOperations);
        this.registerUserInputPort = Objects.requireNonNull(registerUserInputPort);
    }

    @Override
    protected ServerResponse handleInternal(ServerRequest request, TransactionStatus status) {
        return this.registerUserInputPort.registerUser(builder ->
                        builder.name(request.param("name").orElse(null))
                                .password(request.param("password").orElse(null)))
                .onFailure(status::setRollbackOnly)
                .process(this);
    }

    @Override
    public ServerResponse processSuccess(Success result) {
        return ServerResponse.noContent().build();
    }

    @Override
    public ServerResponse processValidationFailed(ValidationFailed result) {
        return ServerResponse
                .badRequest()
                .body(this.createViolationReport(result.validation()));
    }
}
