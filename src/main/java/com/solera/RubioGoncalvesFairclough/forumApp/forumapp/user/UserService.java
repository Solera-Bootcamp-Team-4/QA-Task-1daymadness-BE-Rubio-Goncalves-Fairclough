package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findUsers();
    }

    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }


    /**
     * @param username The username of the user you want to delete
     * @return Could the user be deleted?
     */
    public boolean deleteUser(String username) {
        userRepository.removeUserByUsername(username);
        return true;
    }


    /**
     * @param user The user you want to create
     * @return could the user be created?
     */
    public boolean createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) return false;
        userRepository.insertUser(user);
        return true;
    }

}
