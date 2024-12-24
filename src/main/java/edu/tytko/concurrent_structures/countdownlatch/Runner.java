package edu.tytko.concurrent_structures.countdownlatch;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

public class Runner {


    public static void main(String[] args) throws InterruptedException {


        CountDownLatch readyCountDownLatch = new CountDownLatch(10);
        CountDownLatch startCountDownLatch = new CountDownLatch(1);


        List<Thread> threads = Stream.generate(() -> new Thread(new Worker(readyCountDownLatch, startCountDownLatch)))
                .limit(10)
                .toList();

        threads.forEach(Thread::start);
        readyCountDownLatch.await();
        System.out.println("All threads ready");
        startCountDownLatch.countDown();
    }

}

class Worker implements Runnable {

    private CountDownLatch readyCountDownLatch;
    private CountDownLatch startCountDownLatch;

    public Worker(CountDownLatch readyCountDownLatch, CountDownLatch startCountDownLatch) {
        this.readyCountDownLatch = readyCountDownLatch;
        this.startCountDownLatch = startCountDownLatch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " initialized");
        readyCountDownLatch.countDown();
        try {
            startCountDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " works");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " finished");
    }
}