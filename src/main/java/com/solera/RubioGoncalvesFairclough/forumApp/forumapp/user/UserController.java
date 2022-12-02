package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get a list of users.
     *
     * @return the complete list of users
     */
    @RequestMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        User user = userService.getUser(username);
        System.out.println(username);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>("Username does not exist", HttpStatus.NO_CONTENT);

    }

    @PostMapping("/user/new")
    public ResponseEntity<User> create(@RequestBody User user) {
        return userService.createUser(user)
                ? new ResponseEntity<>(userService.getUser(user.getUsername()), HttpStatus.CREATED)
                : new ResponseEntity<>(userService.getUser(user.getUsername()), HttpStatus.CONFLICT);
    }

    @DeleteMapping("/user/{username}/delete")
    public ResponseEntity<String> delete(@PathVariable("username") String username) {
        return userService.deleteUser(username)
                ? new ResponseEntity<>("User deleted successfuly", HttpStatus.OK)
                : new ResponseEntity<>("Username doesn't exist", HttpStatus.NO_CONTENT);
    }

}
