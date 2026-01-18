package com.example.recipes.specification;

import com.example.recipes.domain.Recipe;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.JoinType;
import java.util.Set;

public class RecipeSpecifications {

    public static Specification<Recipe> isVegetarian(Boolean vegetarian) {
        return (root, query, cb) ->
            vegetarian == null ? null : cb.equal(root.get("vegetarian"), vegetarian);
    }

    public static Specification<Recipe> hasServings(Integer servings) {
        return (root, query, cb) ->
            servings == null ? null : cb.equal(root.get("servings"), servings);
    }

    public static Specification<Recipe> includesIngredients(Set<String> ingredients) {
        return (root, query, cb) -> {
            if (ingredients == null || ingredients.isEmpty()) return null;
            var join = root.join("ingredients", JoinType.INNER);
            return join.in(ingredients);
        };
    }

    public static Specification<Recipe> excludesIngredients(Set<String> ingredients) {
        return (root, query, cb) -> {
            if (ingredients == null || ingredients.isEmpty()) {
                return null;
            }

            query.distinct(true);

            var join = root.join("ingredients", JoinType.LEFT);
            return cb.or(
                    cb.isNull(join),
                    cb.not(join.in(ingredients))
            );
        };
    }


    public static Specification<Recipe> instructionContains(String text) {
        return (root, query, cb) ->
            text == null ? null :
            cb.like(cb.lower(root.get("instructions")), "%" + text.toLowerCase() + "%");
    }
}
