package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapters.RecipeAdapter;
import com.example.smartpantrymanager.models.PantryItem;
import com.example.smartpantrymanager.models.Recipe;
import com.example.smartpantrymanager.utils.RecipeMatcher;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRecipes;
    private TextView textRecipeMessage;

    private RecipeAdapter recipeAdapter;

    private List<Recipe> matchingRecipes;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

        recyclerViewRecipes =
                findViewById(R.id.recyclerViewRecipes);

        textRecipeMessage =
                findViewById(R.id.textRecipeMessage);

        matchingRecipes = new ArrayList<>();

        recipeAdapter = new RecipeAdapter(
                matchingRecipes,
                recipe -> openRecipeDetails(recipe)
        );

        recyclerViewRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerViewRecipes.setAdapter(recipeAdapter);

        db = FirebaseFirestore.getInstance();

        loadRecipes();
    }

    private void loadRecipes() {

        db.collection("pantryItems")
                .get()
                .addOnSuccessListener(pantryDocuments -> {

                    List<PantryItem> pantryItems =
                            new ArrayList<>();

                    for (var document : pantryDocuments) {

                        PantryItem item =
                                document.toObject(
                                        PantryItem.class
                                );

                        pantryItems.add(item);
                    }

                    loadRecipesFromFirestore(pantryItems);
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            this,
                            "Failed to load pantry: "
                                    + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }

    private void loadRecipesFromFirestore(
            List<PantryItem> pantryItems) {

        db.collection("recipes")
                .get()
                .addOnSuccessListener(recipeDocuments -> {

                    matchingRecipes.clear();

                    for (var document : recipeDocuments) {

                        Recipe recipe =
                                document.toObject(
                                        Recipe.class
                                );

                        if (RecipeMatcher.canMakeRecipe(
                                recipe,
                                pantryItems)) {

                            matchingRecipes.add(recipe);
                        }
                    }

                    recipeAdapter.notifyDataSetChanged();

                    updateMessage();
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            this,
                            "Failed to load recipes: "
                                    + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }

    private void updateMessage() {

        if (matchingRecipes.isEmpty()) {

            textRecipeMessage.setText(
                    "No recipes match your pantry yet. "
                            + "Add more ingredients to get suggestions."
            );

        } else {

            textRecipeMessage.setText(
                    matchingRecipes.size()
                            + " recipe(s) can be made with your pantry."
            );
        }
    }

    private void openRecipeDetails(Recipe recipe) {

        Intent intent = new Intent(
                SuggestedRecipesActivity.this,
                RecipeDetailActivity.class
        );

        intent.putExtra(
                "recipeId",
                recipe.getId()
        );

        startActivity(intent);
    }
}