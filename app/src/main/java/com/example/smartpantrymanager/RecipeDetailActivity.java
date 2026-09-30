package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.models.Recipe;
import com.example.smartpantrymanager.models.RecipeIngredient;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView textRecipeName;
    private TextView textRecipeIngredients;
    private TextView textRecipePreparation;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        textRecipeName =
                findViewById(R.id.textRecipeName);

        textRecipeIngredients =
                findViewById(R.id.textRecipeIngredients);

        textRecipePreparation =
                findViewById(R.id.textRecipePreparation);

        db = FirebaseFirestore.getInstance();

        String recipeId =
                getIntent().getStringExtra("recipeId");

        if (recipeId == null || recipeId.isEmpty()) {

            Toast.makeText(
                    this,
                    "Recipe not found",
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

        loadRecipe(recipeId);
    }

    private void loadRecipe(String recipeId) {

        db.collection("recipes")
                .document(recipeId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {

                    if (!documentSnapshot.exists()) {

                        Toast.makeText(
                                this,
                                "Recipe not found",
                                Toast.LENGTH_LONG
                        ).show();

                        finish();
                        return;
                    }

                    Recipe recipe =
                            documentSnapshot.toObject(
                                    Recipe.class
                            );

                    if (recipe == null) {
                        return;
                    }

                    displayRecipe(recipe);
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            this,
                            "Failed to load recipe: "
                                    + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }

    private void displayRecipe(Recipe recipe) {

        textRecipeName.setText(
                recipe.getName()
        );

        StringBuilder ingredients =
                new StringBuilder();

        for (RecipeIngredient ingredient :
                recipe.getIngredients()) {

            ingredients
                    .append("• ")
                    .append(ingredient.getName())
                    .append(" - ")
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }

        textRecipeIngredients.setText(
                ingredients.toString()
        );

        textRecipePreparation.setText(
                recipe.getPreparationSteps()
        );
    }
}