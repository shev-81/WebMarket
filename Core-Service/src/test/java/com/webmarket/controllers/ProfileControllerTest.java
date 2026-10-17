package com.webmarket.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for ProfileController.
 */
@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // -----------------------------------------------------------------------
    // GET /api/v1/profile
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: GET /profile с header username — возвращается ProfileDto с этим именем
    void getCurrentUserInfo_withUsername_shouldReturnProfile() throws Exception {
        mockMvc.perform(get("/api/v1/profile")
                        .header("username", "john_doe")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"));
    }

    @Test
    // Сценарий: GET /profile с пустым username — возвращается ProfileDto с пустой строкой
    void getCurrentUserInfo_withEmptyUsername_shouldReturnEmptyProfile() throws Exception {
        mockMvc.perform(get("/api/v1/profile")
                        .header("username", "")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(""));
    }

    @Test
    // Сценарий: GET /profile без header username — 400 MissingRequestHeaderException
    void getCurrentUserInfo_withoutUsername_shouldFail() throws Exception {
        mockMvc.perform(get("/api/v1/profile")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}