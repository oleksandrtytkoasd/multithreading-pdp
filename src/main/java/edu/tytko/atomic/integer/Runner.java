package edu.tytko.atomic.integer;

import java.util.concurrent.atomic.AtomicInteger;

public class Runner {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();
        Thread t1 = new Thread(counter);
        Thread t2 = new Thread(counter);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Non atomic counter: " + counter.getCounter());

        AtomicCounter atomicCounter = new AtomicCounter();
        t1 = new Thread(atomicCounter);
        t2 = new Thread(atomicCounter);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Atomic counter: " + atomicCounter.getCounter());
    }
}

class Counter implements Runnable {

    private int counter = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                counter++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getCounter() {
        return counter;
    }
}

class AtomicCounter implements Runnable {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                counter.incrementAndGet();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getCounter() {
        return counter.get();
    }
}