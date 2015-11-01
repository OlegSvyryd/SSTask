package app.cache;

import com.softserve.edu.pat.Cache;
import com.softserve.edu.pat.CacheFactory;
import com.softserve.edu.pat.Strategy;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static app.cache.SleepAndDisplay.*;

import static org.junit.Assert.assertEquals;

/**
 * This class testing Cache collection with REPLACE_LEAST_USED strategy
 * @author ol
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestReplaceLeastUsedStrategy {

    private final static int CAPACITY = 5;                                              //capacity
    private final static long LIFETIME = 5000l;                                         //lifetime in cache - 5 second
    private final static Strategy STRATEGY = Strategy.REPLACE_LEAST_USED;               //displacement strategy

    private Integer[] keys = {1, 2, 3, 4, 5};                                           //prepared keys for testing
    private String[] values = {"test1", "test2", "test3", "test4", "test5"};            //prepared values for testing

    private Integer[] additionalKeys = {6, 7};                                          //prepared additional keys for testing
    private String[] additionalValues = {"test6", "test7"};                             //prepared additional values for testing

    private Integer[] additionalKeysTimelife = { 10, 11, 12 };                          //prepared additional keys for testing timelife
    private String[] additionalValuesTimelife = { "test10", "test11", "test12" };       //prepared additional values for testing timelife

    private static Cache<Integer, String> cacheStorage;                                 //cache instance

    @BeforeClass
    public static void runBeforeClass() {
        CacheFactory cacheFactory = new CacheFactory();
        cacheStorage = cacheFactory.getCacheCollection(CAPACITY, LIFETIME, STRATEGY);
    }

    @AfterClass
    public static void runAfterClass() {
        cacheStorage = null;
    }

    @Test
    public void test00PutItems() {
        System.out.println("--- Running test with cache REPLACE_LEAST_USED displacement strategy ---");
        System.out.println("Test put method.");

        System.out.println(" > Initialize cache of 5 items: ");
        int expectedCapacity = keys.length;

        cacheStorage.put(keys[0], values[0]);
        cacheStorage.put(keys[1], values[1]);
        cacheStorage.put(keys[2], values[2]);
        cacheStorage.put(keys[3], values[3]);
        cacheStorage.put(keys[4], values[4]);

        display(cacheStorage);

        Assert.assertNotNull(cacheStorage);
        assertEquals(cacheStorage.size(), expectedCapacity);
    }

    @Test
    public void test01GetItem() {
        System.out.println("Test get method.");

        Integer actualKey = keys[1];
        String expectedValue = values[1];

        String actualValue = cacheStorage.get(actualKey);

        System.out.println(" > Get item from cache: ");
        System.out.println("   Key = " + actualKey + ", value = " + actualValue);

        Assert.assertNotNull(actualValue);
        assertEquals(actualValue, expectedValue);
    }

    @Test
    public void test02RemoveItem() {
        System.out.println("Test remove method.");

        Integer keyToRemove = keys[2];
        String expectedValueToRemove = values[2];

        String actual = cacheStorage.remove(keyToRemove);

        System.out.println(" > Removed item: ");
        System.out.println("   Key = " + keyToRemove + ", value = " + actual);

        Assert.assertNotNull(actual);
        assertEquals(actual, expectedValueToRemove);
        Assert.assertNotEquals(cacheStorage.size(), CAPACITY);
    }

    @Test
    public void test03GetSize() {
        System.out.println("Test size method.");

        int actualSize = cacheStorage.size();
        System.out.println(" > Size of cache: " + actualSize);

        Assert.assertNotNull(actualSize);
    }

    @Test
    public void test04EntrySet() {
        System.out.println("Test entrySet method.\n > Completed.");

        Set entrySet = cacheStorage.entrySet();

        int count = 0;
        Iterator i = entrySet.iterator();
        while (i.hasNext()) {
            count++;
            Assert.assertNotNull(i.next());
        }

        Assert.assertEquals(entrySet.size(), count);
        Assert.assertNotNull(entrySet);
    }

    @Test
    public void test05StrategyDisplacement() {
        System.out.println("Test strategy displacement.");

        System.out.println(" > Entries: ");
        display(cacheStorage);

        System.out.println(" > Put 2 new items but before call get method (" + cacheStorage.get(1) + "): ");
        cacheStorage.put(additionalKeys[0], additionalValues[0]);
        cacheStorage.put(additionalKeys[1], additionalValues[1]);

        System.out.println(" > Entries after adding 2 new items: ");
        display(cacheStorage);

        Assert.assertNotNull(cacheStorage.get(1));

        System.out.println(" > Capacity of cache before adding 2 new items equals 4, " +
                "after adding 2 new items, actual capacity \n" +
                "\tis greater than initial capacity " +
                "and in this strategy, entry which was accessed \n\t" +
                " through \'get(Object key)\' method least number of times is removed from cache.");
    }

    @Test
    public void test06SetCapacity() {
        System.out.println("Test set capacity.");

        int actualCapacity = cacheStorage.getCapacity();

        System.out.println(" > Actual capacity: " + actualCapacity);
        Assert.assertNotNull(actualCapacity);

        System.out.println(" > Set capacity to 6: ");
        cacheStorage.setCapacity(6);

        System.out.println(" > New capacity: " + cacheStorage.getCapacity() + ", cache size: " + cacheStorage.size());
        Assert.assertNotEquals(cacheStorage.getCapacity(), cacheStorage.size());
    }

    @Test
    public void test07TimeLifeAndAutoRemoving() {
        System.out.println("Test timelife and auto removing.");

        long timelife = cacheStorage.getTimelife();
        System.out.println(" > Defined timelife: " + timelife + "ms");
        Assert.assertNotNull(timelife);

        System.out.println(" > Get items keys and create time point in ms: ");

        displayKeys(cacheStorage);

        System.out.println(" > Sleep 3000 ms., set capacity to 10 and add 3 new items: ");
        cacheStorage.setCapacity(10);
        sleep(3000l);

        cacheStorage.put(additionalKeysTimelife[0], additionalValuesTimelife[0]);
        cacheStorage.put(additionalKeysTimelife[1], additionalValuesTimelife[1]);
        cacheStorage.put(additionalKeysTimelife[2], additionalValuesTimelife[2]);

        System.out.println(" > Get all entries after sleep 3000ms: ");
        int expectedAfterAdding = 0;
        for(Map.Entry entry : cacheStorage.entrySet()) {
            System.out.println("   " + entry.getKey());
            expectedAfterAdding++;
        }

        Assert.assertNotNull(cacheStorage);
        Assert.assertEquals(expectedAfterAdding, cacheStorage.size());

        System.out.println(" > And sleep again 3000ms and older elements should be removed");
        sleep(3000l);

        displayKeys(cacheStorage);

        Assert.assertNotNull(cacheStorage);
        Assert.assertNotEquals(expectedAfterAdding, cacheStorage.size());

        System.out.println(" > And sleep 5000ms to remove other items");
        sleep(5000l);

        displayKeys(cacheStorage);

        Assert.assertEquals(cacheStorage.size(), 0);
    }
}
