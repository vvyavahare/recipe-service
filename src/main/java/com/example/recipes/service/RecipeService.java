package com.example.recipes.service;

import com.example.recipes.domain.Recipe;
import com.example.recipes.repository.RecipeRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.example.recipes.specification.RecipeSpecifications.*;

@Service
public class RecipeService {
    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public Recipe save(Recipe recipe) {
        return repository.save(recipe);
    }

    public List<Recipe> findAll() {
        return repository.findAll();
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public List<Recipe> search(
            Boolean vegetarian,
            Integer servings,
            Set<String> includeIngredients,
            Set<String> excludeIngredients,
            String instructionText
    ) {
        Specification<Recipe> spec =
                Specification.where(isVegetarian(vegetarian))
                        .and(hasServings(servings))
                        .and(includesIngredients(includeIngredients))
                        .and(excludesIngredients(excludeIngredients))
                        .and(instructionContains(instructionText));

        return repository.findAll(spec);
    }

}
