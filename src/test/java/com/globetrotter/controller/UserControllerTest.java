package com.globetrotter.controller;

import com.globetrotter.GlobetrotterApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = GlobetrotterApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
public void testRegisterAndScore() throws Exception {
    // Register the user first
    mockMvc.perform(post("/api/user/register")
            .param("username", "TestUser"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("TestUser"));
    
    // Then retrieve the user's score
    mockMvc.perform(get("/api/user/TestUser/score"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correct").exists())
            .andExpect(jsonPath("$.incorrect").exists());
}

}
