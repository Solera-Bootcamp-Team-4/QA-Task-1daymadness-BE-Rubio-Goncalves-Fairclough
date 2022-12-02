package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ThreadController {

    @Autowired
    private ThreadService threadService;

    /**
     * Get a list of threads.
     *
     * @return the complete list of threads
     */
    @RequestMapping("/threads")
    public ResponseEntity<List<Thread>> getAll() {
        return new ResponseEntity<>(threadService.getAllThreads(), HttpStatus.OK);
    }

    @RequestMapping("/thread/{id}")
    public ResponseEntity<?> getOne(@PathVariable int id) {
        Thread thread = threadService.getThread(id);
        return thread != null
                ? new ResponseEntity<>(thread, HttpStatus.OK)
                : new ResponseEntity<>("Thread does not exist", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/thread/new")
    public ResponseEntity<Thread> create(@RequestBody Thread thread) {
        return threadService.createThread(thread)
                ? new ResponseEntity<>(threadService.getThread(thread.getId()), HttpStatus.CREATED)
                : new ResponseEntity<>(threadService.getThread(thread.getId()), HttpStatus.CONFLICT);
    }

    @DeleteMapping("/thread/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        return threadService.deleteThread(id)
                ? new ResponseEntity<>("Thread deleted successfully", HttpStatus.OK)
                : new ResponseEntity<>("Thread doesn't exist", HttpStatus.NO_CONTENT);
    }

}
