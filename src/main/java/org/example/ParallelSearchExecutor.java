package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelSearchExecutor {

    private final int[] array;
    private final int searchValue;
    private final ExecutorService executor;

    public ParallelSearchExecutor(int[] array, int searchValue, ExecutorService executor) {
        this.array = array;
        this.searchValue = searchValue;
        this.executor = executor;
    }

    static class SearchTask implements Callable<Integer> {
        private final int low;
        private final int high;
        private final int[] array;
        private final int searchValue;

        public SearchTask(int[] array, int low, int high, int searchValue) {
            this.array = array;
            this.low = low;
            this.high = high;
            this.searchValue = searchValue;
        }

        @Override
        public Integer call() {
            for (int i = low; i < high; i++) {
                if (array[i] == searchValue) {
                    return i;
                }
            }
            return -1;
        }
    }

    public int search() throws InterruptedException, ExecutionException {
        int step = (int)Math.ceil((double)array.length / Runtime.getRuntime().availableProcessors());

        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < array.length; i += step) {
            final int high = Math.min(array.length, i + step);
            tasks.add(new SearchTask(array, i, high, searchValue));
        }

        List<Future<Integer>> results = executor.invokeAll(tasks);

        for (Future<Integer> result : results) {
            int index = result.get();
            if (index >= 0) {
                return index;
            }
        }

        return -1;
    }
}
