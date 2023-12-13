package org.example;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Integer> {

    private final int[] array;
    private final int low;
    private final int high;
    private final int searchValue;

    public ParallelSearch(int[] array, int low, int high, int searchValue) {
        this.array = array;
        this.low = low;
        this.high = high;
        this.searchValue = searchValue;
    }

    @Override
    protected Integer compute() {
        int length = high - low;

        if(length < 10) {
            for(int i = low; i < high; ++i) {
                if(array[i] == searchValue) return i;
            }
            return -1;
        } else {
            int mid = low + length / 2;
            ParallelSearch leftSearch = new ParallelSearch(array, low, mid, searchValue);
            ParallelSearch rightSearch = new ParallelSearch(array, mid, high, searchValue);
            leftSearch.fork();

            Integer rightResult = rightSearch.compute();
            Integer leftResult = leftSearch.join();

            return Math.max(rightResult, leftResult); // adjust as per your requirement
        }
    }
}
