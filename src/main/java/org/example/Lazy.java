package org.example;

import java.util.function.Supplier;

public class Lazy<T> {
    private final Supplier<T> factory;

    private boolean isInitialized = false;
    private T value;

    public Lazy(Supplier<T> factory) {
        this.factory = factory;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public T get() {
        if (!isInitialized) {
            value = factory.get();
            isInitialized = true;
        }

        return value;
    }
}
