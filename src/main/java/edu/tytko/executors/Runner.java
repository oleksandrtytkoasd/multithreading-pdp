package edu.tytko.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Runner {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        long[] array = new long[1000000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        List<Callable<Long>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new Task(array, i * 200000, (i + 1) * 200000));
        }
        System.out.println(executor.invokeAll(list)
                .stream()
                .mapToLong(t -> {
                    try {
                        return t.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum());
        executor.shutdown();
    }

}

class Task implements Callable<Long> {
    private final long[] array;
    private final int low;
    private final int high;

    public Task(long[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    public Long call() throws Exception {
        long result = 0;
        for (int i = low; i < high; i++) {
            result += array[i];
        }
        return result;
    }
}