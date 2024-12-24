package edu.tytko.concurrent_structures.cyclic_barrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Stream;

public class Runner {

    public static void main(String[] args) {

        List<Integer> list = Collections.synchronizedList(new ArrayList<>());

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            list.stream().max(Integer::compareTo).ifPresent((v) -> {
                System.out.println(Thread.currentThread().getName() + ", max value: " + v);
            });
        });

        List<Thread> threads = Stream.generate(() -> new Thread(new Worker(list, cyclicBarrier)))
                .limit(5)
                .toList();
        threads.forEach(Thread::start);

    }

}

class Worker implements Runnable {

    private List<Integer> list;
    private CyclicBarrier cyclicBarrier;

    public Worker(List<Integer> list, CyclicBarrier cyclicBarrier) {
        this.list = list;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(100));
        }
        try {
            Thread.sleep(random.nextInt(10000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " finished");
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}