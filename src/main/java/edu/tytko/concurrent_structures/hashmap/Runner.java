package edu.tytko.concurrent_structures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Runner {

    public static void main(String[] args) {

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        map.put(1, 0);

        modifyMap(map);
        System.out.println("Conventional map: " + map);

        Map<Integer, Integer> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put(0, 0);
        concurrentMap.put(1, 0);

        modifyMap(concurrentMap);
        System.out.println("Concurrent map: " + concurrentMap);
    }

    private static void modifyMap(Map<Integer, Integer> map) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    map.computeIfPresent(finalI % 2, (k, v) -> v + 1);
                }
            });
            threads.add(thread);
        }
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
