package com.globetrotter.controller;

import com.globetrotter.GlobetrotterApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = GlobetrotterApplication.class)
@AutoConfigureMockMvc
public class DestinationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRandomDestinationEndpoint() throws Exception {
        mockMvc.perform(get("/destinations/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.clues").exists());
    }
}
