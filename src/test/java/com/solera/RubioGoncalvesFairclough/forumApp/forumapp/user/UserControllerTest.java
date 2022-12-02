package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    private final Map<String, User> expectedUsers = new HashMap<>();

    @SneakyThrows
    public static String asJsonString(final Object obj) {
            return new ObjectMapper().writeValueAsString(obj);
    }

    @Test
    public void getAllShouldReturnListOfUsersFromService() throws Exception {
        User expected = new User("JohnDo", "JohnDo@gmail.com");

        expectedUsers.put(expected.getUsername(), expected);
        when(service.getAllUsers())
                .thenReturn(expectedUsers.values().stream().toList());

        assertNotNull(expectedUsers.values().stream().toList().get(0));
        this.mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected.getUsername())))
                .andExpect(content().string(containsString(expected.getEmail())));
    }

    @Test
    public void getSingleUserShouldReturnUserFromService() throws Exception {
        User expected = new User("JohnDo", "JohnDo@gmail.com");
        expectedUsers.put(expected.getUsername(), expected);

        when(service.getUser(anyString())).thenReturn(expected);

        assertNotNull(service.getUser(expected.getUsername()));
        this.mockMvc.perform(get("/api/user/" + expected.getUsername()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected.getUsername())))
                .andExpect(content().string(containsString(expected.getEmail())));
    }

    @Test
    @DisplayName("Should return 201 when calling POST /api/user/new with User json")
    public void createUserShouldReturn201WhenUserCreated() throws Exception {
        User expected = new User("JohnDo", "JohnDo@gmail.com");

        when(service.createUser(Mockito.any(User.class)))
                .then(invocation -> {
                    expectedUsers.put(expected.getUsername(), expected);
                    return expectedUsers.containsKey(expected.getUsername());
                });

        this.mockMvc.perform(MockMvcRequestBuilders
                                .post("/api/user/new")
                                .content(asJsonString(expected))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        System.out.println("Reach this");
        System.out.println(expectedUsers);
        assertFalse(expectedUsers.isEmpty());
    }

    @Test
    @DisplayName("Should return 200 when calling POST /api/user/{username}/delete")
    public void deleteUserShouldReturn200WhenUserDeleted() throws Exception {
        User newUser = new User("JohnDo", "JohnDo@gmail.com");
        expectedUsers.put(newUser.getUsername(), newUser);

        when(service.deleteUser(anyString()))
                .then(invocation -> {
                    expectedUsers.remove(newUser.getUsername());
                    return !expectedUsers.containsKey(newUser.getUsername());
                });

        assertFalse(expectedUsers.isEmpty());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/"+newUser.getUsername()+"/delete"))
                .andDo(print())
                .andExpect(status().isOk());

        assertTrue(expectedUsers.isEmpty());
    }
}
