package edu.tytko.fork_join_pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Runner {
    public static void main(String[] args) {
        long[] array = new long[1000000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(array, 0, array.length);
        Long result = pool.invoke(task);
        System.out.println("Result: " + result);
    }
}

class SumTask extends RecursiveTask<Long> {
    private final long[] array;
    private final int low;
    private final int high;

    public SumTask(long[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected Long compute() {
        if (high - low <= 10) {
            // If the task is small enough, just perform the calculation
            long sum = 0;
            for (int i = low; i < high; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // If the task is too large, divide it into smaller tasks
            int mid = (low + high) / 2;
            SumTask left = new SumTask(array, low, mid);
            SumTask right = new SumTask(array, mid, high);
            left.fork();
            Long rightResult = right.compute();
            Long leftResult = left.join();
            return leftResult + rightResult;
        }
    }
}
