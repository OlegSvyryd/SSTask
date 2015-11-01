package com.softserve.edu.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Thread-safe collection (key-value pair)
 * @param <K> Key in collection
 * @param <V> Value in collection
 * @author ol
 */
public abstract class Cache<K, V> {

    // Constants

    private static final int INITIAL_DELAY = 0;                     //time to delay first execution
    private static final int PERIOD = 2;                            //period between successive executions
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;     //time unit of the initialDelay and period parameters

    protected static final short BAD_CAPACITY = 1;                  //define min permissible value of capacity
    protected static final short BAD_TIMELIFE = 100;                //define min permissible value of timelife

    // Fields

    /**
     * Define max capacity of cache
     */
    protected int capacity;

    /**
     * Time during item exist in cache(timelife of item)
     */
    protected long timelife;

    /**
     * Map that contains cache items
     */
    Map<Key, V> cacheMap;

    /**
     * Scheduler that execute code in interval to monitoring timelife of cache items
     */
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        public Thread newThread(Runnable r) {
            Thread th = new Thread(r);
            th.setDaemon(true);             //marks this thread to exit when the only threads running are all daemon threads.
            return th;
        }
    });

    // Constructor

    public Cache(){
        try {
            //Creates and executes a periodic action
            scheduler.scheduleAtFixedRate(new Runnable() {
                public void run() {
                        Iterator<Key> i = cacheMap.keySet().iterator();     //get entries
                        while (i.hasNext()) {
                            Key key = i.next();
                            if (!key.isLive(System.currentTimeMillis())) {  //check is timelife not out
                                i.remove();                                 //remove item if timelife is out
                            }
                        }
                }
            }, INITIAL_DELAY, PERIOD, TIME_UNIT);                           //execute every 2 seconds again
        } catch(Exception e) {
            throw new IllegalArgumentException("Error in scheduler.");
        }
    }

    // Modification operations

    /**
     * Returns the number of key-value mappings in this map.
     * @return the number of key-value mappings in this map
     */
    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    /**
     * Returns a view of the mappings contained in this map.
     * @return a set view of the mappings contained in this map
     */
    public Set<Map.Entry<Key, V>> entrySet() {
        synchronized (cacheMap) {
            return cacheMap.entrySet();
        }
    }

    /**
     * Return max capacity of map
     * @return Max capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Set max capacity
     * @param capacity New max capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Return timelife
     * @return timelife
     */
    public long getTimelife() {
        return timelife;
    }

    /**
     * Set timelife
     * @param timelife New timelife
     */
    public void setTimelife(long timelife) {
        this.timelife = timelife;
    }

    // Abstract methods

    /**
     * Returns the value to which the specified key is mapped,
     * or null if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     *         null if this map contains no mapping for the key
     */
    public abstract V get(Object key);

    /**
     * Associates the specified value with the specified key in this map
     * (optional operation). If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or
     *         null if there was no mapping for key.
     */
    public abstract V put(K key, V value);

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or
     *         null if there was no mapping for key.
     */
    public abstract V remove(Object key);

    // Comparison and hashing


    /**
     * Compares the specified object with this map for equality.
     *
     * @param o object to be compared for equality with this map
     * @return true if the specified object is equal to this map
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cache cache = (Cache) o;

        if (capacity != cache.capacity) return false;
        if (timelife != cache.timelife) return false;
        if (!cacheMap.equals(cache.cacheMap)) return false;
        if (!scheduler.equals(cache.scheduler)) return false;

        return true;
    }

    /**
     * Returns the hash code value for this map.
     *
     * @return the hash code value for this map
     */
    @Override
    public int hashCode() {
        int result = capacity;
        result = 31 * result + (int) (timelife ^ (timelife >>> 32));
        result = 31 * result + cacheMap.hashCode();
        result = 31 * result + scheduler.hashCode();
        return result;
    }

    /**
     * Key instance of cache.
     * This class contains field that can calculate time when item should be removed from cache
     */
    static class Key {

        private final Object key;       //key
        private long timelife;          //timelife

        public Key(Object key, long timeout) {
            this.key = key;
            this.timelife = System.currentTimeMillis() + timeout;
        }

        public Key(Object key) {
            this.key = key;
        }

        public boolean isLive(long currentTimeMillis) {
            return currentTimeMillis < timelife;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Key other = (Key) obj;
            if (this.key != other.key && (this.key == null || !this.key.equals(other.key))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 43 * hash + (this.key != null ? this.key.hashCode() : 0);
            return hash;
        }

        @Override
        public String toString() {
            return "Key{" + "key=" + key + ", created=" + timelife + '}';
        }
    }
}