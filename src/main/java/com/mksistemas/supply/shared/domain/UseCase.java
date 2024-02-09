package com.mksistemas.supply.shared.domain;

public interface UseCase<TRequest, TResponse> {
    TResponse execute(TRequest request);
}
