package edu.tytko.concurrent_structures.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Semaphore;

public class Runner {

    public static void main(String[] args) throws InterruptedException {

        DBContext db = new DBContext();

        Runnable r = () -> {
            try {
                SqlSession sqlSession = db.getSqlSession();
                System.out.println("Thread " + Thread.currentThread().getId() + " obtains session with id " + sqlSession.getId());
                Thread.sleep(4000);
                System.out.println("Thread " + Thread.currentThread().getId() + " available sessions " + db.availableSessions());
                System.out.println("Thread " + Thread.currentThread().getId() + " releases session with id " + sqlSession.getId());
                db.releaseSqlSession(sqlSession);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(r));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

    }
}

class DBContext {

    private Stack<SqlSession> pool = new Stack<>() {{
        add(new SqlSession(1));
        add(new SqlSession(2));
        add(new SqlSession(3));
    }};

    private Semaphore semaphore = new Semaphore(3);

    public SqlSession getSqlSession() throws InterruptedException {
        semaphore.acquire();
        return pool.pop();
    }

    public void releaseSqlSession(SqlSession sqlSession) {
        pool.push(sqlSession);
        semaphore.release();
    }

    public int availableSessions() {
        return semaphore.availablePermits();
    }
}

class SqlSession {
    private int id;

    public SqlSession(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
