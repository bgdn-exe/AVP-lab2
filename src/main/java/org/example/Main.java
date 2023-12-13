package org.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Test case 1:
        int[] array1 = {1, 2, 3, 4, 5, 6, 69, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        int searchValue1 = 1;
        System.out.println("ForkJoin: Search 1, Expected Index: 0, Actual Index: " + forkJoinSearch(array1, searchValue1));
        System.out.println("CachedThreadPool: Search 1, Expected Index: 0, Actual Index:" + cachedThreadPoolSearch(array1, searchValue1));

        // Test case 2:
        int[] array2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 69, 69, 69};
        int searchValue2 = 69;
        System.out.println("ForkJoin: Search 69, Expected Index: >= 20, Actual Index: " + forkJoinSearch(array2, searchValue2));
        System.out.println("CachedThreadPool: Search 69, Expected Index: >= 20, Actual Index: " + cachedThreadPoolSearch(array2, searchValue2));

        // Test case 3:
        int[] array3 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int searchValue3 = 1;
        System.out.println("ForkJoin: Search 1, Expected Index: >=0, Actual Index: " + forkJoinSearch(array3, searchValue3));
        System.out.println("CachedThreadPool: Search 1, Expected Index: >= 0, Actual Index: " + cachedThreadPoolSearch(array3, searchValue3));

        // Test case 4:
        int[] array4 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 69, 69, 69};
        int searchValue4 = 100;
        System.out.println("ForkJoin: Search 100, Expected Index: -1, Actual Index: " + forkJoinSearch(array4, searchValue4));
        System.out.println("CachedThreadPool: Search 100, Expected Index: -1, Actual Index: " + cachedThreadPoolSearch(array4, searchValue4));
    }

    public static int forkJoinSearch(int[] array, int searchValue) {
        // С использованием forkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelSearch parallelSearch = new ParallelSearch(array, 0, array.length, searchValue);
        return forkJoinPool.invoke(parallelSearch);
    }
    public static int cachedThreadPoolSearch(int[] array, int searchValue) throws ExecutionException, InterruptedException {
        // C использованием произвольного пула потоков, но минимизировать количество используемых потоков
        ExecutorService executor = Executors.newCachedThreadPool();
        ParallelSearchExecutor searchExecutor = new ParallelSearchExecutor(array, searchValue, executor);
        int index = searchExecutor.search();
        executor.shutdown();
        return index;
    }
}
