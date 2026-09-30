package com.example.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.example.smartpantrymanager.utils.RecipeSeeder;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPantry;
    private PantryAdapter pantryAdapter;
    private List<PantryItem> pantryItems;

    private TextView textExpiryTitle;
    private TextView textExpiryWarnings;

    private FirebaseFirestore db;

    private SharedPreferences preferences;

    private static final String PREFS_NAME =
            "SmartPantryPreferences";

    private static final String EXPIRY_REMINDERS =
            "expiryReminders";

    private static final int EXPIRY_WARNING_DAYS = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button buttonAddIngredient =
                findViewById(R.id.buttonAddIngredient);

        Button buttonSuggestedRecipes =
                findViewById(R.id.buttonSuggestedRecipes);

        Button buttonSettings =
                findViewById(R.id.buttonSettings);

        recyclerViewPantry =
                findViewById(R.id.recyclerViewPantry);

        textExpiryTitle =
                findViewById(R.id.textExpiryTitle);

        textExpiryWarnings =
                findViewById(R.id.textExpiryWarnings);

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

        preferences = getSharedPreferences(
                PREFS_NAME,
                MODE_PRIVATE
        );

        RecipeSeeder.seedRecipes(db);

        buttonAddIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );

            startActivity(intent);
        });

        buttonSuggestedRecipes.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );

            startActivity(intent);
        });

        buttonSettings.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
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
                                document.toObject(
                                        PantryItem.class
                                );

                        pantryItems.add(item);
                    }

                    pantryAdapter.notifyDataSetChanged();

                    checkExpiryWarnings();
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

    private void checkExpiryWarnings() {

        boolean remindersEnabled =
                preferences.getBoolean(
                        EXPIRY_REMINDERS,
                        true
                );

        if (!remindersEnabled) {

            textExpiryTitle.setVisibility(View.GONE);
            textExpiryWarnings.setVisibility(View.GONE);

            return;
        }

        List<String> warnings =
                new ArrayList<>();

        SimpleDateFormat dateFormat =
                new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                );

        dateFormat.setLenient(false);

        Date today = new Date();

        for (PantryItem item : pantryItems) {

            String expiryDate =
                    item.getExpiryDate();

            if (expiryDate == null ||
                    expiryDate.trim().isEmpty()) {

                continue;
            }

            try {

                Date expiry =
                        dateFormat.parse(
                                expiryDate.trim()
                        );

                if (expiry == null) {
                    continue;
                }

                long difference =
                        expiry.getTime()
                                - today.getTime();

                long daysRemaining =
                        TimeUnit.MILLISECONDS.toDays(
                                difference
                        );

                if (daysRemaining < 0) {

                    warnings.add(
                            "• " + item.getName()
                                    + " - Expired on "
                                    + expiryDate
                    );

                } else if (
                        daysRemaining
                                <= EXPIRY_WARNING_DAYS) {

                    warnings.add(
                            "• " + item.getName()
                                    + " - Expires in "
                                    + daysRemaining
                                    + " day(s) ("
                                    + expiryDate
                                    + ")"
                    );
                }

            } catch (ParseException e) {

                // Ignore invalid expiry dates.
            }
        }

        if (warnings.isEmpty()) {

            textExpiryTitle.setVisibility(View.GONE);
            textExpiryWarnings.setVisibility(View.GONE);

        } else {

            textExpiryTitle.setVisibility(View.VISIBLE);
            textExpiryWarnings.setVisibility(View.VISIBLE);

            StringBuilder warningText =
                    new StringBuilder();

            for (String warning : warnings) {

                warningText
                        .append(warning)
                        .append("\n");
            }

            textExpiryWarnings.setText(
                    warningText.toString().trim()
            );
        }
    }

    private void editIngredient(PantryItem item) {

        Intent intent = new Intent(
                MainActivity.this,
                AddEditIngredientActivity.class
        );

        intent.putExtra(
                "ingredientId",
                item.getId()
        );

        intent.putExtra(
                "ingredientName",
                item.getName()
        );

        intent.putExtra(
                "ingredientQuantity",
                item.getQuantity()
        );

        intent.putExtra(
                "ingredientUnit",
                item.getUnit()
        );

        intent.putExtra(
                "ingredientExpiry",
                item.getExpiryDate()
        );

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
                            "Failed to delete ingredient: "
                                    + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }
}