package com.example.smartpantrymanager.models;

import java.util.List;

public class Recipe {

    private String id;
    private String name;
    private List<RecipeIngredient> ingredients;
    private String preparationSteps;

    public Recipe() {
        // Required by Firestore
    }

    public Recipe(
            String id,
            String name,
            List<RecipeIngredient> ingredients,
            String preparationSteps) {

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparationSteps() {
        return preparationSteps;
    }

    public void setPreparationSteps(String preparationSteps) {
        this.preparationSteps = preparationSteps;
    }
}