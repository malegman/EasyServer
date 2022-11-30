package com.example.easyserver.validation;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Регистрирует поля с выявленными нарушениями
 *
 * @param value        значение, которое необходимо проверить
 * @param rule         валидирующая функция - правило проверки
 * @param property     наименование значения
 * @param errorMessage сообщение ошибки
 * @param <T>          класс объекта, поле которого необходимо проверить
 * @param <F>          класс проверяемого поля
 */
public record FieldConstraints<T, F>(Function<T, F> value, Predicate<F> rule, String property, String errorMessage)
        implements BiConsumer<Validation, T> {

    @Override
    public void accept(Validation validation, T t) {
        if (!this.rule.test(this.value.apply(t))) {
            validation.registerViolation(this.property, this.errorMessage);
        }
    }
}
