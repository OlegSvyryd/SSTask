package com.softserve.edu.pat;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Instance of cache with REPLACE_OLDEST strategy
 * @param <K> Key
 * @param <V> Value
 * @author ol
 */
public class ReplaceOldestCache<K, V> extends Cache<K, V> {

    // Fields

    /**
     * Queue for save key insertion order
     */
    private Queue<K> keyInsertionOrder = new LinkedList<K>();

    // Constructor

    public ReplaceOldestCache(int capacity, long timelife) {
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
        cacheMap = new HashMap<Key, V>(getCapacity());  //create instance of hashmap
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
            V previous = cacheMap.put(new Key(key, getTimelife()), value);  //put new key with timelife and item get previous for return
            keyInsertionOrder.remove(new Key(key));                         //remove from queue
            keyInsertionOrder.add((K) new Key(key));                        //insert again in queue

            if (cacheMap.size() > getCapacity()) {
                K oldest = keyInsertionOrder.poll();                        //remove from head of queue
                cacheMap.remove(oldest);                                    //remove from cache
            }

            return previous;
        }
    }

    @Override
    public V remove(Object key) {
        synchronized (cacheMap) {
            keyInsertionOrder.remove(new Key(key));
            return cacheMap.remove(new Key(key));
        }
    }

    // Comparing and hashing

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReplaceOldestCache that = (ReplaceOldestCache) o;

        if (keyInsertionOrder != null ? !keyInsertionOrder.equals(that.keyInsertionOrder) : that.keyInsertionOrder != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return keyInsertionOrder != null ? keyInsertionOrder.hashCode() : 0;
    }
}
