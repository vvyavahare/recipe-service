package com.example.recipes.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Recipe {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private boolean vegetarian;
    private int servings;

    @ElementCollection
    private Set<String> ingredients;

    @Column(length = 5000)
    private String instructions;

}
