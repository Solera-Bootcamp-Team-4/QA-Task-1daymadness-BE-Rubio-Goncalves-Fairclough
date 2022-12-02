package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.forum;

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

@WebMvcTest(ForumController.class)
public class ForumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForumService service;

    private final Map<String, Forum> expectedForums = new HashMap<>();

    @SneakyThrows
    public static String asJsonString(final Object obj) {
            return new ObjectMapper().writeValueAsString(obj);
    }

    @Test
    public void getAllShouldReturnListOfForumsFromService() throws Exception {
        Forum expected = new Forum("Catpics");

        expectedForums.put(expected.getName(), expected);
        when(service.getAllForums())
                .thenReturn(expectedForums.values().stream().toList());

        assertNotNull(expectedForums.values().stream().toList().get(0));
        this.mockMvc.perform(get("/api/forums"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected.getName())));
    }

    @Test
    public void getSingleForumShouldReturnForumFromService() throws Exception {
        Forum expected = new Forum("Catpics");
        expectedForums.put(expected.getName(), expected);

        when(service.getForum(anyString())).thenReturn(expected);

        assertNotNull(service.getForum(expected.getName()));
        this.mockMvc.perform(get("/api/forum/" + expected.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected.getName())));
    }

    @Test
    @DisplayName("Should return 201 when calling POST /api/forum/new with Forum json")
    public void createForumShouldReturn201WhenForumCreated() throws Exception {
        Forum expected = new Forum("Catpics");

        when(service.createForum(Mockito.any(Forum.class)))
                .then(invocation -> {
                    expectedForums.put(expected.getName(), expected);
                    return expectedForums.containsKey(expected.getName());
                });

        this.mockMvc.perform(MockMvcRequestBuilders
                                .post("/api/forum/new")
                                .content(asJsonString(expected))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        System.out.println("Reach this");
        System.out.println(expectedForums);
        assertFalse(expectedForums.isEmpty());
    }

    @Test
    @DisplayName("Should return 200 when calling POST /api/forum/{name}/delete")
    public void deleteForumShouldReturn200WhenForumDeleted() throws Exception {
        Forum newForum = new Forum("Catpics");
        expectedForums.put(newForum.getName(), newForum);

        when(service.deleteForum(anyString()))
                .then(invocation -> {
                    expectedForums.remove(newForum.getName());
                    return !expectedForums.containsKey(newForum.getName());
                });

        assertFalse(expectedForums.isEmpty());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/forum/"+newForum.getName()+"/delete"))
                .andDo(print())
                .andExpect(status().isOk());

        assertTrue(expectedForums.isEmpty());
    }
}
