package edu.tytko.concurrent_structures.copy_on_write_array;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Runner {
    public static void main(String[] args) throws InterruptedException {

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> iterator = list.iterator();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });
        thread.start();
        System.out.println("Conventional ArrayList: ");
        try {
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Exception caught: " + e);
        }
        thread.join();

        System.out.println("Copy on write ArrayList: ");

        List<Integer> copyOnWriteList = new CopyOnWriteArrayList<>();
        copyOnWriteList.add(1);
        copyOnWriteList.add(2);
        copyOnWriteList.add(3);

        iterator = copyOnWriteList.iterator();
        thread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                copyOnWriteList.add(i);
            }
        });
        thread.start();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        thread.join();

        System.out.println("Take iterator again");
        iterator = copyOnWriteList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
