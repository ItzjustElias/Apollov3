package org.elias.apollo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ObjectPool<T> {
    private final Supplier<T> factory;
    private final List<T> available;
    private final List<T> inUse;

    public ObjectPool(Supplier<T> factory) {
        this.factory = factory;
        this.available = new ArrayList<>();
        this.inUse = new ArrayList<>();
    }

    public T acquire() {
        if (available.isEmpty()) {
            available.add(factory.get());
        }
        T instance = available.removeLast();
        inUse.add(instance);
        return instance;
    }

    public void release(T instance) {
        inUse.remove(instance);
        available.add(instance);
    }

    public int getInUseCount() {
        return inUse.size();
    }

    public int getAvailableCount() {
        return available.size();
    }
}
