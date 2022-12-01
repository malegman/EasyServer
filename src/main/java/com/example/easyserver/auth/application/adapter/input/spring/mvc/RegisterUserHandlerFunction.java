package com.example.easyserver.auth.application.adapter.input.spring.mvc;

import com.example.easyserver.auth.application.port.input.RegisterUserInputPort;
import com.example.easyserver.auth.application.port.input.RegisterUserInputPort.Result.Processor;
import com.example.easyserver.auth.application.port.input.RegisterUserInputPort.Result.Success;
import com.example.easyserver.auth.application.port.input.RegisterUserInputPort.Result.ValidationFailed;
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

    private record Payload(String name, String password) {
    }

    @Override
    protected ServerResponse handleInternal(ServerRequest request, TransactionStatus status) throws Exception {
        final var payload = request.body(Payload.class);
        return this.registerUserInputPort.registerUser(builder ->
                        builder.name(payload.name())
                                .password(payload.password()))
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
