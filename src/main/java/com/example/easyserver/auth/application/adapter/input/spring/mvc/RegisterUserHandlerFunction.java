package com.example.easyserver.auth.application.adapter.input.spring.mvc;

import com.example.easyserver.auth.application.port.input.RegisterUserInputPort;
import com.example.easyserver.auth.application.port.input.RegisterUserInputPort.Payload;
import com.example.easyserver.auth.application.port.input.RegisterUserInputPort.Result.*;
import com.example.easyserver.common.AbstractHandlerFunction;
import com.example.easyserver.validation.Validation;
import com.example.easyserver.validation.Violation;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;
import java.util.Map;
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

    private record RequestPayload(String name, String password) {
    }

    @Override
    protected ServerResponse handleInternal(ServerRequest request, TransactionStatus status) throws Exception {
        final var payload = request.body(RequestPayload.class);
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
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.createViolationReport(result.validation()));
    }

    @Override
    public ServerResponse processExecutionFailed(ExecutionFailed result) {
        if (result.exception() instanceof DataAccessException e) {
            final var cause = e.getCause();
            if (cause != null) {
                if (cause.getMessage().contains("(c_login)=")) {
                    return ServerResponse
                            .badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(this.createViolationReport(new Validation(Map.of(
                                    Payload.PROP_NAME,
                                    List.of(new Violation(Payload.ERROR_NAME_EXISTS))
                            ))));
                }
            }
        }
        throw new RuntimeException(result.exception());
    }
}
