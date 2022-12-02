package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    private User expected = new User("JohnDo", "JohnDo@gmail.com");
    private Map<String, User> expectedUsers = new HashMap<>();

    @BeforeEach
    public void init() {
        expectedUsers.put(expected.getUsername(), expected);
    }


    @Test
    public void getAllShouldReturnListOfUsersFromService() throws Exception {
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
        when(service.getUser(anyString()))
                .thenReturn(expected);
        assertNotNull(service.getUser(expected.getUsername()));
        this.mockMvc.perform(get("/api/user?username=" + expected.getUsername()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected.getUsername())))
                .andExpect(content().string(containsString(expected.getEmail())));
    }

}
