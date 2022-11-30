package com.example.easyserver.auth.application.port.output;

import com.example.easyserver.auth.application.dto.RegisterUserDto;

/**
 * Выходной порт ядра для регистрации пользователя
 */
@FunctionalInterface
public interface RegisterUserOutputPort {

    void registerUser(RegisterUserDto dto);
}
