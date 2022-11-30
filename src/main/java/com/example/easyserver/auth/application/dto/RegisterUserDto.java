package com.example.easyserver.auth.application.dto;

/**
 * DTO для регистрации пользователя
 *
 * @param name     имя пользователя
 * @param password пароль пользователя
 */
public record RegisterUserDto(String name, String password) {
}
