package app.cache;

import com.softserve.edu.cache.Cache;

import java.util.Map;

/**
 * Additional features from testing
 * @author ol
 */
public class SleepAndDisplay {

    /**
     * Sleep thread
     * @param milliseconds Time to sleeping (in ms.)
     */
    static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display entry on console
     * @param cacheStorage Instance of cache
     */
    static void display(Cache<Integer, String> cacheStorage) {
        for(Map.Entry entry : cacheStorage.entrySet()) {
            System.out.println("   " + entry);
        }
    }

    /**
     * Display entry keys on console
     * @param cacheStorage Instance of cache
     */
    static void displayKeys(Cache<Integer, String> cacheStorage) {
        for(Map.Entry entry : cacheStorage.entrySet()) {
            System.out.println("   " + entry.getKey());
        }
    }
}