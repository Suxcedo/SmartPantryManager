package com.example.smartpantrymanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.models.PantryItem;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryItems;

    public PantryAdapter(List<PantryItem> pantryItems) {
        this.pantryItems = pantryItems;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position) {

        PantryItem item = pantryItems.get(position);

        holder.textIngredientName.setText(item.getName());

        String quantityText = item.getQuantity() + " " + item.getUnit();
        holder.textIngredientQuantity.setText(quantityText);

        if (item.getExpiryDate() == null ||
                item.getExpiryDate().isEmpty()) {

            holder.textIngredientExpiry.setText("No expiry date");

        } else {

            holder.textIngredientExpiry.setText(
                    "Expiry: " + item.getExpiryDate()
            );
        }
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView textIngredientName;
        TextView textIngredientQuantity;
        TextView textIngredientExpiry;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            textIngredientName =
                    itemView.findViewById(R.id.textIngredientName);

            textIngredientQuantity =
                    itemView.findViewById(R.id.textIngredientQuantity);

            textIngredientExpiry =
                    itemView.findViewById(R.id.textIngredientExpiry);
        }
    }
}