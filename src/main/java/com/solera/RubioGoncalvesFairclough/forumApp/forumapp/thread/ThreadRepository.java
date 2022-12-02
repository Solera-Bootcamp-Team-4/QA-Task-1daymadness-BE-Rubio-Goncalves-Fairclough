package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.thread;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ThreadRepository {

    private static final Map<Integer, Thread> threads = new HashMap<>();

    public ThreadRepository() {
    }

    public Thread findThreadById(int id) {
        if (threads.containsKey(id)) {
            return threads.get(id);
        }
        return null;
    }

    /**
     * @param thread The thread you want to insert to the database
     */
    public void insertThread(Thread thread) {
        threads.put(thread.getId(), thread);
    }

    public boolean existsById(int id) {
        return threads.containsKey(id);
    }

    public void removeThreadById(int id) {
        threads.remove(id);
    }

    public List<Thread> findAll() {
        return threads.values().stream().toList();
    }
}
