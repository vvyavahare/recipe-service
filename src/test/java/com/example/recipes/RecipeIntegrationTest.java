package com.example.recipes;

import com.example.recipes.domain.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class RecipeIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void dbProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    TestRestTemplate rest;

    @Test
    void shouldCreateAndFilterRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Oven Potatoes");
        recipe.setVegetarian(true);
        recipe.setServings(4);
        recipe.setIngredients(Set.of("potato"));
        recipe.setInstructions("Bake in oven");

        rest.postForEntity("/api/recipes", recipe, Recipe.class);

        var response = rest.getForEntity(
                "/api/recipes?vegetarian=true&instructionText=oven",
                Recipe[].class
        );

        assertThat(response.getBody()).hasSize(1);
    }
}
