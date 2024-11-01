package org.elias.apollo;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class Cache<K, V> {
    final Map<K, WeakReference<V>> cache = new HashMap<>();

    /**
     * Gets an item from the cache.
     *
     * @param key The key of the item to retrieve.
     * @return The cached item, or null if not found or cleared.
     */
    public V get(K key) {
        WeakReference<V> weakReference = cache.get(key);
        return (weakReference != null) ? weakReference.get() : null;
    }

    /**
     * Puts an item in the cache.
     *
     * @param key The key of the item to cache.
     * @param value The item to cache.
     */
    public void put(K key, V value) {
        cache.put(key, new WeakReference<>(value));
    }

    /**
     * Clears the cache.
     */
    public void clear() {
        cache.clear();
    }
}
