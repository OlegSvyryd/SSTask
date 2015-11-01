/**
 * THIS PACKAGE CONTAIN SOLUTION FOR TECHNICAL TASK FOR JAVA SOFTWARE ENGINEERS CANDIDATES
 * <P>
 * IMPLEMENTS A COLLECTION WHICH HOLDS KEY-VALUE PAIRS (CACHE) WHICH APPLIES THE REQUIREMENTS..
 * PLEASE, EXECUTE JUNIT TEST IN TURN (ORDER OF METHOD IN TEST - NAME ASCENDING OF METHOD)
 *
 * REPOSITORY: https://github.com/OlegSvyryd/SSTask
 *
 * RESULT OF TESTING:
 --- Running test with cache REPLACE_LEAST_USED displacement strategy ---
 Test put method.
 > Initialize cache of 5 items:
    Key{key=1, created=1446421218615}=test1
    Key{key=2, created=1446421218615}=test2
    Key{key=3, created=1446421218615}=test3
    Key{key=4, created=1446421218615}=test4
    Key{key=5, created=1446421218615}=test5
 Test get method.
 > Get item from cache:
    Key = 2, value = test2
 Test remove method.
 > Removed item:
    Key = 3, value = test3
 Test size method.
 > Size of cache: 4
 Test entrySet method.
 > Completed.
 Test strategy displacement.
 > Entries:
    Key{key=1, created=1446421218615}=test1
    Key{key=4, created=1446421218615}=test4
    Key{key=5, created=1446421218615}=test5
    Key{key=2, created=1446421218615}=test2
 > Put 2 new items but before call get method (test1):
 > Entries after adding 2 new items:
    Key{key=5, created=1446421218615}=test5
    Key{key=2, created=1446421218615}=test2
    Key{key=1, created=1446421218615}=test1
    Key{key=6, created=1446421218634}=test6
    Key{key=7, created=1446421218634}=test7
 > Capacity of cache before adding 2 new items equals 4, after adding 2 new items, actual capacity
    is greater than initial capacity and in this strategy, entry which was accessed
    through 'get(Object key)' method least number of times is removed from cache.
 Test set capacity.
 > Actual capacity: 5
 > Set capacity to 6:
 > New capacity: 6, cache size: 5
 Test timelife and auto removing.
 > Defined timelife: 5000ms
 > Get items keys and create time point in ms:
    Key{key=5, created=1446421218615}
    Key{key=2, created=1446421218615}
    Key{key=6, created=1446421218634}
    Key{key=7, created=1446421218634}
    Key{key=1, created=1446421218615}
 > Sleep 3000 ms., set capacity to 10 and add 3 new items:
 > Get all entries after sleep 3000ms:
    Key{key=5, created=1446421218615}
    Key{key=2, created=1446421218615}
    Key{key=6, created=1446421218634}
    Key{key=7, created=1446421218634}
    Key{key=1, created=1446421218615}
    Key{key=10, created=1446421221645}
    Key{key=11, created=1446421221645}
    Key{key=12, created=1446421221645}
 > And sleep again 3000ms and older elements should be removed
    Key{key=10, created=1446421221645}
    Key{key=11, created=1446421221645}
    Key{key=12, created=1446421221645}
 > And sleep 5000ms to remove other items
 --- Running test with cache REPLACE_OLDER displacement strategy ---
 Test put method.
 > Initialize cache of 5 items:
    Key{key=3, created=1446421371037}=test3
    Key{key=4, created=1446421371037}=test4
    Key{key=5, created=1446421371037}=test5
    Key{key=1, created=1446421371037}=test1
    Key{key=2, created=1446421371037}=test2
 Test get method.
 > Get item from cache:
    Key = 2, value = test2
 Test remove method.
 > Removed item:
    Key = 3, value = test3
 Test size method.
 > Size of cache: 4
 Test entrySet method.
 > Completed.
 Test strategy displacement.
 > Entries:
    Key{key=4, created=1446421371037}=test4
    Key{key=5, created=1446421371037}=test5
    Key{key=1, created=1446421371037}=test1
    Key{key=2, created=1446421371037}=test2
 > Put 2 new items:
 > Entries after adding 2 new items:
    Key{key=4, created=1446421371037}=test4
    Key{key=5, created=1446421371037}=test5
    Key{key=6, created=1446421371149}=test6
    Key{key=7, created=1446421371149}=test7
    Key{key=2, created=1446421371037}=test2
 > Capacity of cache before adding 2 new items equals 4, after adding 2 new items, actual capacity
    is greater than initial capacity and in this strategy, oldest entry(test1) is removed from cache.
 Test set capacity.
 > Actual capacity: 5
 > Set capacity to 6:
 > New capacity: 6, cache size: 5
 Test timelife and auto removing.
 > Defined timelife: 5000ms
 > Get items keys and create time point in ms:
    Key{key=4, created=1446421371037}
    Key{key=5, created=1446421371037}
    Key{key=6, created=1446421371149}
    Key{key=7, created=1446421371149}
    Key{key=2, created=1446421371037}
 > Sleep 3000 ms., set capacity to 10 and add 3 new items:
 > Get all entries after sleep 3000ms:
    Key{key=4, created=1446421371037}
    Key{key=5, created=1446421371037}
    Key{key=6, created=1446421371149}
    Key{key=7, created=1446421371149}
    Key{key=10, created=1446421374162}
    Key{key=11, created=1446421374162}
    Key{key=12, created=1446421374162}
    Key{key=2, created=1446421371037}
 > And sleep again 3000ms and older elements should be removed
    Key{key=10, created=1446421374162}
    Key{key=11, created=1446421374162}
    Key{key=12, created=1446421374162}
 > And sleep 5000ms to remove other items
 *
 *
 * @AUTHOR Svyryd Oleg
 * @VERSION 1.0
 */
package com.softserve.edu.cache;