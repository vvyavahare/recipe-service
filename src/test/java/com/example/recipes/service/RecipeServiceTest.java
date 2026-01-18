package com.example.recipes.service;

import com.example.recipes.domain.Recipe;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.service.RecipeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    private final RecipeRepository repository = Mockito.mock(RecipeRepository.class);
    private final RecipeService service = new RecipeService(repository);

    @Test
    void shouldCreateRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");

        Mockito.when(repository.save(recipe)).thenReturn(recipe);

        Recipe saved = service.save(recipe);

        Mockito.verify(repository).save(recipe);
        Assertions.assertThat(saved).isSameAs(recipe);
    }

    @Test
    void shouldDeleteRecipeById() {
        UUID id = UUID.randomUUID();

        service.delete(id);

        Mockito.verify(repository).deleteById(id);
    }

    @Test
    void shouldBuildSpecificationAndSearch() {
        Mockito.when(repository.findAll(ArgumentMatchers.any(Specification.class)))
                .thenReturn(List.of());

        service.search(
                true,
                4,
                Set.of("potato"),
                Set.of("salmon"),
                "oven"
        );

        ArgumentCaptor<Specification<Recipe>> captor =
                ArgumentCaptor.forClass(Specification.class);

        Mockito.verify(repository).findAll(captor.capture());

        Assertions.assertThat(captor.getValue()).isNotNull();
    }
}
