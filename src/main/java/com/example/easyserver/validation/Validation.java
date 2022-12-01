package com.example.easyserver.validation;

import lombok.EqualsAndHashCode;

import java.util.*;

/**
 * Валидатор для регистрации нарушений
 */
@EqualsAndHashCode(of = "violations")
public class Validation {

    private final Map<String, List<Violation>> violations;

    public Validation() {
        this.violations = new HashMap<>();
    }

    public Validation(final Map<String, List<Violation>> violations) {
        this.violations = Objects.requireNonNull(violations);
    }

    public void registerViolation(String property, String errorMessage) {
        this.violations.computeIfAbsent(property, f -> new ArrayList<>())
                .add(new Violation(errorMessage));
    }

    public boolean isValid() {
        return this.violations.isEmpty();
    }

    public Map<String, List<Violation>> getViolations() {
        return violations;
    }
}
