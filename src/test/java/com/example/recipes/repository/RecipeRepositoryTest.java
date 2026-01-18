package com.example.recipes.repository;

import com.example.recipes.domain.Recipe;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.specification.RecipeSpecifications;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RecipeRepositoryTest {

    @Autowired
    RecipeRepository repository;

    @Test
    void shouldFilterVegetarianRecipes() {
        Recipe veg = new Recipe();
        veg.setName("Veg Curry");
        veg.setVegetarian(true);
        veg.setServings(2);
        veg.setIngredients(Set.of("potato"));
        veg.setInstructions("Bake in oven");

        Recipe nonVeg = new Recipe();
        nonVeg.setName("Salmon Dish");
        nonVeg.setVegetarian(false);
        nonVeg.setServings(2);
        nonVeg.setIngredients(Set.of("salmon"));
        nonVeg.setInstructions("Pan fry");

        repository.saveAll(List.of(veg, nonVeg));

        var result = repository.findAll(
                RecipeSpecifications.isVegetarian(true)
        );

        assertThat(result)
                .hasSize(1)
                .allMatch(Recipe::isVegetarian);
    }

    @Test
    void shouldExcludeIngredient() {
        Recipe recipe = new Recipe();
        recipe.setName("Veg Bake");
        recipe.setVegetarian(true);
        recipe.setServings(4);
        recipe.setIngredients(Set.of("potato", "cheese"));
        recipe.setInstructions("Use oven");

        repository.save(recipe);

        var result = repository.findAll(
                RecipeSpecifications.excludesIngredients(Set.of("salmon"))
        );

        assertThat(result).hasSize(1);
    }
}
