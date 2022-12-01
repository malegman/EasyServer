package com.example.easyserver.auth.application.usecase;

import com.example.easyserver.auth.application.dto.RegisterUserDto;
import com.example.easyserver.auth.application.port.input.RegisterUserInputPort.Result;
import com.example.easyserver.auth.application.port.input.RegisterUserInputPort.Payload;
import com.example.easyserver.auth.application.port.output.RegisterUserOutputPort;
import com.example.easyserver.validation.SelfValidatingValidator;
import com.example.easyserver.validation.Validation;
import com.example.easyserver.validation.Violation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @Mock
    RegisterUserOutputPort registerUserOutputPort;

    RegisterUserUseCase useCase;

    @BeforeEach
    void setUp() {
        this.useCase = new RegisterUserUseCase(SelfValidatingValidator.INSTANCE,
                this.registerUserOutputPort);
    }

    @Test
    void registerUser_PayloadIsValid_ReturnsSuccessResult() {

        // given
        final var name = "User";
        final var password = "password";

        doNothing().when(this.registerUserOutputPort).registerUser(
                new RegisterUserDto(name, password));

        // when
        final var result = this.useCase.registerUser(builder ->
                builder.name(name)
                        .password(password));

        // then
        assertEquals(Result.success(), result);
        verifyNoMoreInteractions(this.registerUserOutputPort);
    }

    @Test
    void registerUser_PayloadIsInvalid_ReturnsValidationFailedResult() {

        // given
        final var name = " ";
        final var password = " ";

        // when
        final var result = this.useCase.registerUser(builder ->
                builder.name(name)
                        .password(password));

        // then
        assertEquals(Result.validationFailed(new Validation(Map.of(
                Payload.PROP_NAME,
                List.of(new Violation(Payload.ERROR_NAME)),
                Payload.PROP_PASSWORD,
                List.of(new Violation(Payload.ERROR_PASSWORD))
        ))), result);
        verifyNoInteractions(this.registerUserOutputPort);
    }
}
