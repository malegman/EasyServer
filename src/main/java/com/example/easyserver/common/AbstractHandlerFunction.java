package com.example.easyserver.common;

import com.example.easyserver.validation.Validation;
import com.example.easyserver.validation.Violation;
import com.example.easyserver.validation.ViolationsReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Общий для всех запросов сервера обработчик запросов
 */
@Slf4j
public abstract class AbstractHandlerFunction implements HandlerFunction<ServerResponse> {

    private final TransactionOperations transactionOperations;

    protected AbstractHandlerFunction(
            final TransactionOperations transactionOperations) {
        this.transactionOperations = Objects.requireNonNull(transactionOperations);
    }

    protected ViolationsReport createViolationReport(final Validation validation) {
        return new ViolationsReport(validation.getViolations().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(Violation::message)
                                .toList())));
    }

    @Override
    public ServerResponse handle(final ServerRequest request) {
        try {
            return this.transactionOperations.execute(status -> {
                try {
                    return this.handleInternal(request, status);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            final var exceptionId = UUID.randomUUID().toString();
            final var message = e.getMessage();
            log.error("Error ID: " + exceptionId + ": " + message, e);
            return ServerResponse
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", message));
        }
    }

    protected abstract ServerResponse handleInternal(ServerRequest request, TransactionStatus status) throws Exception;
}
