package com.example.easyserver.common;

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

@Slf4j
public abstract class AbstractHandlerFunction implements HandlerFunction<ServerResponse> {

    private final TransactionOperations transactionOperations;

    public AbstractHandlerFunction(
            final TransactionOperations transactionOperations) {
        this.transactionOperations = Objects.requireNonNull(transactionOperations);
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
