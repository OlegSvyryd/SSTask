package com.softserve.edu.pat;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Instance of cache with REPLACE_LEAST_USED strategy
 * @param <K> Key
 * @param <V> Value
 * @author ol
 */
public class LRUCache<K, V> extends Cache<K, V>{

    // Constants

    private final int CAPACITY = getCapacity() + 1;     //capacity
    private final float LOAD_FACTOR = 0.75f;            //load factor of linked hash map
    private final boolean ACCESS_ORDER = true;          //the ordering mode - true for access-order, false for insertion-order

    // Constructor

    public LRUCache(int capacity, long timelife){
        if (capacity < BAD_CAPACITY) {
            throw new IllegalArgumentException(
                    "Capacity must be greater than 0");
        }

        if (timelife < BAD_TIMELIFE) {
            throw new IllegalArgumentException(
                    "Timelife must be longest than 100ms");
        }

        setCapacity(capacity);
        setTimelife(timelife);
        cacheMap = createLRUMap();
    }

    /**
     * Create instance of linked hash map that implement LRU displacement strategy
     * @param <K> Key
     * @param <V> Value
     * @return Instance of linked hash map (implement LRU displacement strategy)
     */
    public<K, V> Map<K, V> createLRUMap() {
        return new LinkedHashMap<K, V>(CAPACITY, LOAD_FACTOR, ACCESS_ORDER) {

            /**
             * This method provides the implementor with the opportunity to remove the eldest entry each time a new one is added.
             * @param eldest The least recently accessed entry
             * @return Returns true if this map should remove its eldest entry
             */
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > getCapacity();
            }
        };
    }

    // Methods

    @Override
    public V get(Object key) {
        synchronized (cacheMap) {
            return cacheMap.get(new Key(key));
        }
    }

    @Override
    public V put(K key, V value) {
        synchronized (cacheMap) {
            cacheMap.put(new Key(key, getTimelife()), value);   //put new key with timelife
            return value;
        }
    }

    @Override
    public V remove(Object key) {
        synchronized (cacheMap) {
            return cacheMap.remove(new Key(key));
        }
    }

    // Comparing and hashing

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LRUCache lruCache = (LRUCache) o;

        if (ACCESS_ORDER != lruCache.ACCESS_ORDER) return false;
        if (CAPACITY != lruCache.CAPACITY) return false;
        if (Float.compare(lruCache.LOAD_FACTOR, LOAD_FACTOR) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + CAPACITY;
        result = 31 * result + (LOAD_FACTOR != +0.0f ? Float.floatToIntBits(LOAD_FACTOR) : 0);
        result = 31 * result + (ACCESS_ORDER ? 1 : 0);
        return result;
    }
}
