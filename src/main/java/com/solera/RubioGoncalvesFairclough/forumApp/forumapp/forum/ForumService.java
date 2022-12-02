package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {

    @Autowired
    ForumRepository forumRepository;

    public List<Forum> getAllForums() {
        return forumRepository.findForums();
    }

    public Forum getForum(String name) {
        return forumRepository.findForumByName(name);
    }


    /**
     * @param name The name of the forum you want to delete
     * @return Could the forum be deleted?
     */
    public boolean deleteForum(String name) {
        forumRepository.removeForumByName(name);
        return true;
    }


    /**
     * @param forum The forum you want to create
     * @return could the forum be created?
     */
    public boolean createForum(Forum forum) {
        if (forumRepository.existsByName(forum.getName())) return false;
        forumRepository.insertForum(forum);
        return true;
    }

}
