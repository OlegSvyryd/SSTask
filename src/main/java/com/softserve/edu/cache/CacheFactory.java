package com.softserve.edu.cache;

/**
 * Factory for get cache instance
 * @author ol
 */
public class CacheFactory {

    private final static int TRUE_FROM_COMPARE_TO = 0;      //return true when result of method compareTo is true

    /**
     * Return instance of cache for appropriate strategy
     * @param capacity Cache capacity
     * @param timelife Timelife of items in cache
     * @param strategy Displacement strategy
     * @return Instance of cache for appropriate strategy
     */
    public Cache getCacheCollection(int capacity, long timelife, Enum<Strategy> strategy) {
        if(strategy == null) {
            return null;
        }
        if(strategy.compareTo(Strategy.REPLACE_OLDEST) == TRUE_FROM_COMPARE_TO){
            return new ReplaceOldestCache(capacity, timelife);
        }
        else if(strategy.compareTo(Strategy.REPLACE_LEAST_USED) == TRUE_FROM_COMPARE_TO){
            return new LRUCache(capacity, timelife);
        }
        else {
            throw new IllegalArgumentException("Error in cache factory method, bad strategy.");
        }
    }
}