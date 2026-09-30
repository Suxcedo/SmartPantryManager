package com.example.smartpantrymanager.utils;

import com.example.smartpantrymanager.models.PantryItem;
import com.example.smartpantrymanager.models.Recipe;
import com.example.smartpantrymanager.models.RecipeIngredient;

import java.util.List;
import java.util.Locale;

public class RecipeMatcher {

    public static boolean canMakeRecipe(
            Recipe recipe,
            List<PantryItem> pantryItems) {

        if (recipe.getIngredients() == null ||
                recipe.getIngredients().isEmpty()) {

            return false;
        }

        for (RecipeIngredient requiredIngredient :
                recipe.getIngredients()) {

            double availableQuantity = 0;

            for (PantryItem pantryItem : pantryItems) {

                if (ingredientsMatch(
                        pantryItem.getName(),
                        requiredIngredient.getName())) {

                    double convertedQuantity =
                            convertToBaseUnit(
                                    pantryItem.getQuantity(),
                                    pantryItem.getUnit()
                            );

                    double requiredQuantity =
                            convertToBaseUnit(
                                    requiredIngredient.getQuantity(),
                                    requiredIngredient.getUnit()
                            );

                    if (sameUnitType(
                            pantryItem.getUnit(),
                            requiredIngredient.getUnit())) {

                        availableQuantity += convertedQuantity;
                    }
                }
            }

            double requiredQuantity =
                    convertToBaseUnit(
                            requiredIngredient.getQuantity(),
                            requiredIngredient.getUnit()
                    );

            if (availableQuantity < requiredQuantity) {
                return false;
            }
        }

        return true;
    }

    private static boolean ingredientsMatch(
            String pantryName,
            String recipeName) {

        if (pantryName == null || recipeName == null) {
            return false;
        }

        String pantry =
                normalizeIngredientName(pantryName);

        String recipe =
                normalizeIngredientName(recipeName);

        return pantry.equals(recipe);
    }

    private static String normalizeIngredientName(
            String ingredientName) {

        String name = ingredientName
                .toLowerCase(Locale.ROOT)
                .trim();

        if (name.endsWith("ies")) {

            name = name.substring(
                    0,
                    name.length() - 3
            ) + "y";

        } else if (name.endsWith("es")) {

            name = name.substring(
                    0,
                    name.length() - 2
            );

        } else if (name.endsWith("s")) {

            name = name.substring(
                    0,
                    name.length() - 1
            );
        }

        return name;
    }

    private static boolean sameUnitType(
            String firstUnit,
            String secondUnit) {

        String first = normalizeUnit(firstUnit);
        String second = normalizeUnit(secondUnit);

        return getUnitType(first).equals(
                getUnitType(second)
        );
    }

    private static String getUnitType(String unit) {

        switch (unit) {

            case "g":
            case "kg":
                return "weight";

            case "ml":
            case "l":
            case "litre":
            case "litres":
            case "liter":
            case "liters":
                return "volume";

            case "piece":
            case "pieces":
            case "pc":
            case "pcs":
                return "count";

            case "cup":
            case "cups":
                return "cup";

            case "tablespoon":
            case "tablespoons":
            case "tbsp":
                return "tablespoon";

            case "teaspoon":
            case "teaspoons":
            case "tsp":
                return "teaspoon";

            case "slice":
            case "slices":
                return "slice";

            case "loaf":
            case "loaves":
                return "loaf";

            default:
                return unit;
        }
    }

    private static String normalizeUnit(String unit) {

        if (unit == null) {
            return "";
        }

        return unit
                .toLowerCase(Locale.ROOT)
                .trim();
    }

    private static double convertToBaseUnit(
            double quantity,
            String unit) {

        String normalizedUnit =
                normalizeUnit(unit);

        switch (normalizedUnit) {

            case "kg":
                return quantity * 1000;

            case "g":
                return quantity;

            case "l":
            case "litre":
            case "litres":
            case "liter":
            case "liters":
                return quantity * 1000;

            case "ml":
                return quantity;

            case "tablespoon":
            case "tablespoons":
            case "tbsp":
                return quantity;

            case "teaspoon":
            case "teaspoons":
            case "tsp":
                return quantity;

            case "cup":
            case "cups":
                return quantity;

            case "piece":
            case "pieces":
            case "pc":
            case "pcs":
                return quantity;

            case "slice":
            case "slices":
                return quantity;

            case "loaf":
            case "loaves":
                return quantity;

            default:
                return quantity;
        }
    }
}