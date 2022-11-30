package com.example.easyserver.auth.application.usecase;

import com.example.easyserver.auth.application.dto.RegisterUserDto;
import com.example.easyserver.auth.application.port.input.RegisterUserInputPort;
import com.example.easyserver.auth.application.port.output.RegisterUserOutputPort;
import com.example.easyserver.validation.Validator;

import java.util.Objects;

public final class RegisterUserUseCase implements RegisterUserInputPort {

    private final Validator validator;
    private final RegisterUserOutputPort registerUserOutputPort;

    public RegisterUserUseCase(final Validator validator,
                               final RegisterUserOutputPort registerUserOutputPort) {
        this.validator = Objects.requireNonNull(validator);
        this.registerUserOutputPort = Objects.requireNonNull(registerUserOutputPort);
    }

    @Override
    public Result registerUser(Payload payload) {

        final var validation = this.validator.validate(payload);
        if (!validation.isValid()) {
            return Result.validationFailed(validation);
        }

        this.registerUserOutputPort.registerUser(
                new RegisterUserDto(payload.name(), payload.password()));
        return Result.success();
    }
}
