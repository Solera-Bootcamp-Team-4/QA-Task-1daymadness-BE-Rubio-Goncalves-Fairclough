package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.forum;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ForumRepository {

    private static final Map<String, Forum> forums = new HashMap<>();

    public ForumRepository() {
    }

    public Forum findForumByName(String name) {
        if (forums.containsKey(name)) {
            return forums.get(name);
        }
        return null;
    }

    /**
     * @param forum The forum you want to insert to the database
     */
    public void insertForum(Forum forum) {
        forums.put(forum.getName(), forum);
    }

    public boolean existsByName(String name) {
        return forums.containsKey(name);
    }

    public void removeForumByName(String name) {
        forums.remove(name);
    }

    public List<Forum> findForums() {
        return forums.values().stream().toList();
    }
}
