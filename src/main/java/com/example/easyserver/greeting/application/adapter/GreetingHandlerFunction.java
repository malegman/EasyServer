package com.example.easyserver.greeting.application.adapter;

import com.example.easyserver.common.AbstractHandlerFunction;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

public final class GreetingHandlerFunction extends AbstractHandlerFunction {

    public GreetingHandlerFunction(TransactionOperations transactionOperations) {
        super(transactionOperations);
    }

    @Override
    protected ServerResponse handleInternal(ServerRequest request, TransactionStatus status) {
        return ServerResponse
                .ok()
                .body("Hello from Easy Server!");
    }
}
