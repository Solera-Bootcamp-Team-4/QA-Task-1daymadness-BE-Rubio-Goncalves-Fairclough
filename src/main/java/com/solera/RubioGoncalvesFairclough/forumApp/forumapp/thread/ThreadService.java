package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadService {

    @Autowired
    ThreadRepository threadRepository;

    public List<Thread> getAllThreads() {
        return threadRepository.findAll();
    }

    public Thread getThread(int id) {
        return threadRepository.findThreadById(id);
    }


    /**
     * @param id The id of the thread you want to delete
     * @return Could the thread be deleted?
     */
    public boolean deleteThread(int id) {
        threadRepository.removeThreadById(id);
        return true;
    }


    /**
     * @param thread The thread you want to create
     * @return could the thread be created?
     */
    public boolean createThread(Thread thread) {
        if (threadRepository.existsById(thread.getId())) return false;
        threadRepository.insertThread(thread);
        return true;
    }

}
