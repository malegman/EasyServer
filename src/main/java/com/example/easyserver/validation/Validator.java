package com.example.easyserver.validation;

/**
 * Компонент для валидации объектов
 */
@FunctionalInterface
public interface Validator {

    Validation validate(Object object);
}
