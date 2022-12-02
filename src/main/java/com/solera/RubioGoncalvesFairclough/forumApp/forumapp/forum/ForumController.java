package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ForumController {

    @Autowired
    private ForumService forumService;

    /**
     * Get a list of forums.
     *
     * @return the complete list of forums
     */
    @RequestMapping("/forums")
    public ResponseEntity<List<Forum>> getAll() {
        return new ResponseEntity<>(forumService.getAllForums(), HttpStatus.OK);
    }

    @RequestMapping("/forum/{name}")
    public ResponseEntity<?> getOne(@PathVariable String name) {
        Forum forum = forumService.getForum(name);
        return forum != null
                ? new ResponseEntity<>(forum, HttpStatus.OK)
                : new ResponseEntity<>("Forum does not exist", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/forum/new")
    public ResponseEntity<Forum> create(@RequestBody Forum forum) {
        return forumService.createForum(forum)
                ? new ResponseEntity<>(forumService.getForum(forum.getName()), HttpStatus.CREATED)
                : new ResponseEntity<>(forumService.getForum(forum.getName()), HttpStatus.CONFLICT);
    }

    @DeleteMapping("/forum/{forumname}/delete")
    public ResponseEntity<String> delete(@PathVariable("forumname") String forumname) {
        return forumService.deleteForum(forumname)
                ? new ResponseEntity<>("Forum deleted successfuly", HttpStatus.OK)
                : new ResponseEntity<>("Forum doesn't exist", HttpStatus.NO_CONTENT);
    }

}
