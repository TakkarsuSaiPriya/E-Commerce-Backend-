package com.cts.ecommerce.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)  // ✅ IMPORTANT FIX
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateCart() throws Exception {

        mockMvc.perform(post("/cart/create"))
                .andExpect(status().isOk());
    }

    @Test
    void testCartNotFound() throws Exception {

        mockMvc.perform(get("/cart/999"))
                .andExpect(status().isNotFound());
    }
}