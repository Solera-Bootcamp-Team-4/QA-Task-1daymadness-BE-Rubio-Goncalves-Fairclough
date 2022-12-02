package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.thread;

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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ThreadController.class)
public class ThreadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThreadService service;

    private final Map<Integer, Thread> fakeThreadDb = new HashMap<>();

    @SneakyThrows
    public static String asJsonString(final Object obj) {
            return new ObjectMapper().writeValueAsString(obj);
    }

    @Test
    public void getAllShouldReturnListOfThreadsFromService() throws Exception {
        Thread expected = new Thread(1, "Amazing blep", "JohnDo");

        fakeThreadDb.put(expected.getId(), expected);
        when(service.getAllThreads())
                .thenReturn(fakeThreadDb.values().stream().toList());

        assertNotNull(fakeThreadDb.values().stream().toList().get(0));
        this.mockMvc.perform(get("/api/threads"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(Integer.toString(expected.getId()))));
    }

    @Test
    public void getSingleThreadShouldReturnThreadFromService() throws Exception {
        Thread expected = new Thread(1, "Amazing blep", "JohnDo");
        fakeThreadDb.put(expected.getId(), expected);

        when(service.getThread(anyInt())).thenReturn(expected);

        assertNotNull(service.getThread(expected.getId()));
        this.mockMvc.perform(get("/api/thread/" + expected.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(Integer.toString(expected.getId()))));
    }

    @Test
    @DisplayName("Should return 201 when calling POST /api/thread/new with Thread json")
    public void createThreadShouldReturn201WhenThreadCreated() throws Exception {
        Thread expected = new Thread(1, "Amazing blep", "JohnDo");

        when(service.createThread(Mockito.any(Thread.class)))
                .then(invocation -> {
                    fakeThreadDb.put(expected.getId(), expected);
                    return fakeThreadDb.containsKey(expected.getId());
                });

        this.mockMvc.perform(MockMvcRequestBuilders
                                .post("/api/thread/new")
                                .content(asJsonString(expected))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        System.out.println("Reach this");
        System.out.println(fakeThreadDb);
        assertFalse(fakeThreadDb.isEmpty());
    }

    @Test
    @DisplayName("Should return 200 when calling POST /api/thread/{id}/delete")
    public void deleteThreadShouldReturn200WhenThreadDeleted() throws Exception {
        Thread newThread = new Thread(1, "Amazing blep", "JohnDo");
        fakeThreadDb.put(newThread.getId(), newThread);

        when(service.deleteThread(anyInt()))
                .then(invocation -> {
                    fakeThreadDb.remove(newThread.getId());
                    return !fakeThreadDb.containsKey(newThread.getId());
                });

        assertFalse(fakeThreadDb.isEmpty());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/thread/"+ newThread.getId()+"/delete"))
                .andDo(print())
                .andExpect(status().isOk());

        assertTrue(fakeThreadDb.isEmpty());
    }
}
