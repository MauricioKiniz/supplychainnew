package com.mksistemas.supply.shared.domain;

import java.util.Objects;
import java.util.function.Consumer;

public class ServiceUtils {
    private ServiceUtils() {}

    public static void checkElementEquality(
        String newElement, String oldElement,
        Consumer<String> consumer
    ) {
        if (Objects.nonNull(newElement) && !newElement.equals(oldElement)) {
            consumer.accept(newElement);
        }
    }

}
