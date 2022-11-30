package com.example.easyserver.auth.application.port.input;

import com.example.easyserver.validation.FieldConstraints;
import com.example.easyserver.validation.SelfValidating;
import com.example.easyserver.validation.Validation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

/**
 * Входной порт ядра для регистрации пользователя
 */
@FunctionalInterface
public interface RegisterUserInputPort {

    Result registerUser(Payload payload);

    default Result registerUser(Consumer<Payload.Builder> payloadBuilderCustomizer) {
        final var builder = new Payload.Builder();
        payloadBuilderCustomizer.accept(builder);

        return this.registerUser(builder.build());
    }

    record Payload(String name, String password)
            implements SelfValidating {

        public static final String PROP_NAME = "name";
        public static final String PROP_PASSWORD = "password";

        public static final String ERROR_NAME = "login.name.error";
        public static final String ERROR_PASSWORD = "login.name.password";

        public static final List<FieldConstraints<Payload, ?>> VALIDATING_RULES = List.of(
                new FieldConstraints<>(Payload::name, FieldConstraints.PREDICATE_STRING_NOT_BLANK,
                        PROP_NAME, ERROR_NAME),
                new FieldConstraints<>(Payload::password, FieldConstraints.PREDICATE_STRING_NOT_BLANK,
                        PROP_PASSWORD, ERROR_PASSWORD));

        private Payload(Builder builder) {
            this(builder.name, builder.password);
        }

        @Override
        public Validation validate() {
            final var validation = new Validation();
            VALIDATING_RULES.forEach(rule -> rule.accept(validation, this));

            return validation;
        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Builder {

            private String name;
            private String password;

            public Builder name(String value) {
                this.name = value;
                return this;
            }

            public Builder password(String value) {
                this.password = value;
                return this;
            }

            private Payload build() {
                return new Payload(this);
            }
        }
    }

    sealed interface Result {

        default boolean isFailure() {
            return true;
        }

        default Result onFailure(final Runnable runnable) {
            if (this.isFailure()) {
                runnable.run();
            }
            return this;
        }

        static Success success() {
            return Success.INSTANCE;
        }

        static ValidationFailed validationFailed(final Validation validation) {
            return new ValidationFailed(validation);
        }

        <T> T process(Processor<T> processor);

        enum Success implements Result {
            INSTANCE;

            @Override
            public boolean isFailure() {
                return false;
            }

            @Override
            public <T> T process(Processor<T> processor) {
                return processor.processSuccess(this);
            }
        }

        record ValidationFailed(Validation validation) implements Result {

            @Override
            public <T> T process(Processor<T> processor) {
                return processor.processValidationFailed(this);
            }
        }

        interface Processor<T> {

            T processSuccess(Success result);

            T processValidationFailed(ValidationFailed result);
        }
    }
}
