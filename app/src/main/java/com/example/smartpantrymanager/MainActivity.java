package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapters.PantryAdapter;
import com.example.smartpantrymanager.models.PantryItem;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPantry;
    private PantryAdapter pantryAdapter;
    private List<PantryItem> pantryItems;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button buttonAddIngredient = findViewById(R.id.buttonAddIngredient);
        recyclerViewPantry = findViewById(R.id.recyclerViewPantry);

        pantryItems = new ArrayList<>();

        pantryAdapter = new PantryAdapter(
                pantryItems,
                item -> editIngredient(item),
                item -> deleteIngredient(item)
        );

        recyclerViewPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerViewPantry.setAdapter(pantryAdapter);

        db = FirebaseFirestore.getInstance();

        buttonAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );

            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPantryItems();
    }

    private void loadPantryItems() {

        db.collection("pantryItems")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    pantryItems.clear();

                    for (var document : queryDocumentSnapshots) {

                        PantryItem item =
                                document.toObject(PantryItem.class);

                        pantryItems.add(item);
                    }

                    pantryAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            this,
                            "Failed to load pantry: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }

    private void editIngredient(PantryItem item) {

        Intent intent = new Intent(
                MainActivity.this,
                AddEditIngredientActivity.class
        );

        intent.putExtra("ingredientId", item.getId());
        intent.putExtra("ingredientName", item.getName());
        intent.putExtra("ingredientQuantity", item.getQuantity());
        intent.putExtra("ingredientUnit", item.getUnit());
        intent.putExtra("ingredientExpiry", item.getExpiryDate());

        startActivity(intent);
    }

    private void deleteIngredient(PantryItem item) {

        db.collection("pantryItems")
                .document(item.getId())
                .delete()
                .addOnSuccessListener(unused -> {

                    Toast.makeText(
                            this,
                            "Ingredient deleted",
                            Toast.LENGTH_SHORT
                    ).show();

                    loadPantryItems();
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            this,
                            "Failed to delete ingredient: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }
}