package com.softserve.edu.pat;

/**
 * Factory method for get cache instance
 * @author ol
 */
public class CacheFactory {
    public Cache getCacheCollection(int capacity, long timelife, Enum<Strategy> strategy) {
        if(strategy == null) {
            return null;
        }
        if(strategy.compareTo(Strategy.REPLACE_OLDEST) == 0){
            return new ReplaceOldestCache(capacity, timelife);
        }
        else if(strategy.compareTo(Strategy.REPLACE_LEAST_USED) == 0){
            return new LRUCache(capacity, timelife);
        }
        else {
            throw new IllegalArgumentException("Error in cache factory method, bad strategy.");
        }
    }
}
