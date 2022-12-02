package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private static final Map<String, User> users = new HashMap<>();

    public UserRepository() {
        users.put("JohnDo", new User("JohnDo", "johnDo@gmail.com"));
    }

    public User findUserByUsername(String username) {
        if (users.containsKey(username)) {
            return users.get(username);
        }
        return null;
    }

    /**
     * @param user The user you want to insert to the database
     */
    public void insertUser(User user) {
        users.put(user.getUsername(), user);
    }

    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }

    public void removeUserByUsername(String username) {
        users.remove(username);
    }

    public List<User> findUsers() {
        return users.values().stream().toList();
    }
}
