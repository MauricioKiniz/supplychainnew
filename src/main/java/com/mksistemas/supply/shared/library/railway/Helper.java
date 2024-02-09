package com.mksistemas.supply.shared.library.railway;

import java.util.function.Consumer;
import java.util.function.Function;

public final class Helper {
    private static final Unit unit = new Unit();

    public static Unit getUnit() {
        return unit;
    }

    public static <T> Function<T, Unit> toFunc(Consumer<T> action, T value) {
        action.accept(value);
        return t -> new Unit();
    }

    public static record Unit() {}
}