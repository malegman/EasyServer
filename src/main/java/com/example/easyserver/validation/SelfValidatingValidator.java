package com.example.easyserver.validation;

/**
 * Валидатор самовалидирующихся объектов
 */
public enum SelfValidatingValidator implements Validator {
    INSTANCE;

    @Override
    public Validation validate(Object object) {
        if (object instanceof SelfValidating) {
            return ((SelfValidating) object).validate();
        }

        throw new IllegalArgumentException("Object " + object + " isn't self validating.");
    }
}
