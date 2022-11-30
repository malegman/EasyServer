package com.example.easyserver.validation;

/**
 * Нарушение, выявленное в запросе
 *
 * @param message сообщение о нарушении
 */
public record Violation(String message) {
}
