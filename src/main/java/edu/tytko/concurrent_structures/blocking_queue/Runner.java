package edu.tytko.concurrent_structures.blocking_queue;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Runner {
    public static void main(String[] args) {

        BlockingQueue<Data> queue = new ArrayBlockingQueue<>(16);

        List<Thread> producers = List.of(new Thread(new Producer(queue)), new Thread(new Producer(queue)), new Thread(new Producer(queue)));

        List<Thread> consumers = List.of(new Thread(new Consumer(queue)), new Thread(new Consumer(queue)));

        consumers.forEach(Thread::start);
        producers.forEach(Thread::start);

        consumers.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }
}

class Producer implements Runnable {

    private final BlockingQueue<Data> queue;

    public Producer(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        long threadId = Thread.currentThread().threadId();
        for (int i = 0; i < 10; i++) {
            try {
                queue.put(new Data(threadId, i));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Consumer implements Runnable {

    private final BlockingQueue<Data> queue;

    public Consumer(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Data data = queue.poll(5, TimeUnit.SECONDS);
                if (data == null) {
                    break;
                }
                System.out.println(data);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Data {
    private Long thread;
    private int value;

    public Data(Long thread, int value) {
        this.thread = thread;
        this.value = value;
    }

    public Long getThread() {
        return thread;
    }

    public void setThread(Long thread) {
        this.thread = thread;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Data{" +
                "thread=" + thread +
                ", value=" + value +
                '}';
    }
}