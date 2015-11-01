package com.softserve.edu.pat;

/**
 * This enumeration define displacement strategy
 * @author ol
 */
public enum Strategy {
    /**
     * The oldest entry is removed from the cache and new one is inserted instead.
     */
    REPLACE_OLDEST,

    /**
     * The entry which was accessed through get(Object key) method for the least
     * number of times is removed from the cache and new one is inserted instead.
     */
    REPLACE_LEAST_USED
}
