package com.mksistemas.supply.shared.library.test;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class BaseTest<TRequest, TResponse> {

    private Supplier<TRequest> commandSupplier;
    private Function<TRequest, TRequest> givenFunction;
    private Function<TRequest, TResponse> whenFunction;
    private BiConsumer<TRequest, TResponse> thenBiConsumer;
    private BiConsumer<TRequest, Exception> thenExceptionBiConsumer;

    public BaseTest<TRequest, TResponse> commandSupplier(
        Supplier<TRequest> supplier
    ) {
        this.commandSupplier = supplier;
        return this;
    }

    public BaseTest<TRequest, TResponse> given(
        Function<TRequest, TRequest> givenFunction
    ) {
        this.givenFunction = givenFunction;
        return this;
    }

    public BaseTest<TRequest, TResponse> when(
        Function<TRequest, TResponse> whenFunction
    ) {
        this.whenFunction = whenFunction;
        return this;
    }

    public BaseTest<TRequest, TResponse> then(
        BiConsumer<TRequest, TResponse> thenBiConsumer
    ) {
        this.thenBiConsumer = thenBiConsumer;
        return this;
    }

    public BaseTest<TRequest, TResponse> thenOnException(
        BiConsumer<TRequest, Exception> thenExceptionBiConsumer
    ) {
        this.thenExceptionBiConsumer = thenExceptionBiConsumer;
        return this;
    }

    public TResponse execute() {
        boolean executed = false;
        TRequest request = givenFunction.apply(commandSupplier.get());
        try {
            TResponse response = whenFunction.apply(request);
            executed = true;
            thenBiConsumer.accept(request, response);
            return response;
        } catch (Exception e) {
            if (executed)
                throw e;
            if (Objects.nonNull(thenExceptionBiConsumer))
                thenExceptionBiConsumer.accept(request, e);
            return null;
        }
    }

}
