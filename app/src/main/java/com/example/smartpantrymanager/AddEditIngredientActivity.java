package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.models.PantryItem;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText editIngredientName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiryDate;
    private Button buttonSaveIngredient;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        editIngredientName = findViewById(R.id.editIngredientName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiryDate = findViewById(R.id.editExpiryDate);
        buttonSaveIngredient = findViewById(R.id.buttonSaveIngredient);

        db = FirebaseFirestore.getInstance();

        buttonSaveIngredient.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {

        Toast.makeText(this, "Save button clicked", Toast.LENGTH_SHORT).show();

        String name = editIngredientName.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiryDate = editExpiryDate.getText().toString().trim();

        if (name.isEmpty()) {
            editIngredientName.setError("Enter an ingredient name");
            return;
        }

        if (quantityText.isEmpty()) {
            editQuantity.setError("Enter a quantity");
            return;
        }

        if (unit.isEmpty()) {
            editUnit.setError("Enter a unit");
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            editQuantity.setError("Enter a valid quantity");
            return;
        }

        if (quantity <= 0) {
            editQuantity.setError("Quantity must be greater than 0");
            return;
        }

        Toast.makeText(
                this,
                "Validation passed",
                Toast.LENGTH_SHORT
        ).show();

        Toast.makeText(
                this,
                "Starting Firestore save",
                Toast.LENGTH_SHORT
        ).show();

        String documentId = db.collection("pantryItems")
                .document()
                .getId();

        PantryItem pantryItem = new PantryItem(
                documentId,
                name,
                quantity,
                unit,
                expiryDate
        );
        Toast.makeText(
                this,
                "Pantry item created",
                Toast.LENGTH_SHORT
        ).show();

        db.collection("pantryItems")
                .document(documentId)
                .set(pantryItem)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(
                            this,
                            "Ingredient saved successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(
                            this,
                            "Failed to save ingredient: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }
}