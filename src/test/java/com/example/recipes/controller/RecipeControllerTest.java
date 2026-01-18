package com.example.recipes.controller;

import com.example.recipes.api.RecipeController;
import com.example.recipes.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RecipeService service;

    @Test
    void shouldReturn200WhenSearchingRecipes() throws Exception {
        when(service.search(null, null, null, null, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk());
    }
}
