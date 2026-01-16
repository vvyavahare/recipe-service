package com.example.recipes.api;

import com.example.recipes.domain.Recipe;
import com.example.recipes.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @PostMapping
    public Recipe create(@RequestBody Recipe recipe) {
        return service.save(recipe);
    }

    @GetMapping("/all")
    public List<Recipe> getAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping
    public List<Recipe> search(
            @RequestParam(required = false) Boolean vegetarian,
            @RequestParam(required = false) Integer servings,
            @RequestParam(required = false) Set<String> includeIngredients,
            @RequestParam(required = false) Set<String> excludeIngredients,
            @RequestParam(required = false) String instructionText
    ) {
        return service.search(
                vegetarian,
                servings,
                includeIngredients,
                excludeIngredients,
                instructionText
        );
    }

}
