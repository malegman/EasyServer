package com.example.easyserver.validation;

import java.util.List;
import java.util.Map;

/**
 * Ответ сервера при обнаружении нарушений в запросе
 *
 * @param violations нарушения
 */
public record ViolationsReport(Map<String, List<String>> violations) {
}
