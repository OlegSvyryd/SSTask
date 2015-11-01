package app.cache;

import com.softserve.edu.pat.Cache;

import java.util.Map;

/**
 * Created by ol on 01.11.2015.
 */
public class SleepAndDisplay {
    protected static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void display(Cache<Integer, String> cacheStorage) {
        for(Map.Entry entry : cacheStorage.entrySet()) {
            System.out.println("   " + entry);
        }
    }

    static void displayKeys(Cache<Integer, String> cacheStorage) {
        for(Map.Entry entry : cacheStorage.entrySet()) {
            System.out.println("   " + entry.getKey());
        }
    }
}
