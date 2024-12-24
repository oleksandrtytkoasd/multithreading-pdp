package edu.tytko.atomic.reference;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Runner {

    public static void main(String[] args) throws InterruptedException {

        Runnable r = () -> {
            Singleton instance = Singleton.getInstance();
            System.out.println(instance.hashCode());
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

class Singleton {
    private static AtomicReference<Singleton> instance = new AtomicReference<>();

    private Singleton() {}

    public static Singleton getInstance() {
        Singleton singleton = instance.get();
        if (singleton == null) {
            singleton = new Singleton();
            if (!instance.compareAndSet(null, singleton)) {
                singleton = instance.get();
            }
        }
        return singleton;
    }
}