package com.example.smartpantrymanager.utils;

import com.example.smartpantrymanager.models.Recipe;
import com.example.smartpantrymanager.models.RecipeIngredient;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class RecipeSeeder {

    public static void seedRecipes(FirebaseFirestore db) {

        db.collection("recipes")
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if (!queryDocumentSnapshots.isEmpty()) {
                        return;
                    }

                    List<Recipe> recipes = Arrays.asList(

                            // 1. Bunny Chow
                            new Recipe(
                                    "bunny_chow",
                                    "Bunny Chow",
                                    Arrays.asList(
                                            new RecipeIngredient("Bread", 1, "loaf"),
                                            new RecipeIngredient("Chicken", 500, "g"),
                                            new RecipeIngredient("Potatoes", 3, "pieces"),
                                            new RecipeIngredient("Tomato", 3, "pieces"),
                                            new RecipeIngredient("Onion", 2, "pieces"),
                                            new RecipeIngredient("Curry Powder", 2, "tablespoons"),
                                            new RecipeIngredient("Cooking Oil", 3, "tablespoons")
                                    ),
                                    "Fry the onions and chicken in cooking oil. "
                                            + "Add tomatoes, potatoes and curry powder. "
                                            + "Add water and simmer until the chicken and potatoes are cooked. "
                                            + "Cut out the centre of the bread and fill it with the curry."
                            ),

                            // 2. Bobotie
                            new Recipe(
                                    "bobotie",
                                    "Bobotie",
                                    Arrays.asList(
                                            new RecipeIngredient("Beef", 500, "g"),
                                            new RecipeIngredient("Onion", 2, "pieces"),
                                            new RecipeIngredient("Bread", 2, "slices"),
                                            new RecipeIngredient("Milk", 1, "cup"),
                                            new RecipeIngredient("Eggs", 2, "pieces"),
                                            new RecipeIngredient("Curry Powder", 2, "tablespoons"),
                                            new RecipeIngredient("Salt", 1, "teaspoon")
                                    ),
                                    "Fry the onions and beef until cooked. "
                                            + "Soak the bread in milk and mix it into the beef. "
                                            + "Add curry powder and salt. "
                                            + "Place the mixture in a baking dish. "
                                            + "Cover with beaten eggs and milk, then bake until golden."
                            ),

                            // 3. Chakalaka and Pap
                            new Recipe(
                                    "chakalaka_pap",
                                    "Chakalaka and Pap",
                                    Arrays.asList(
                                            new RecipeIngredient("Maize Meal", 2, "cups"),
                                            new RecipeIngredient("Carrots", 3, "pieces"),
                                            new RecipeIngredient("Tomato", 3, "pieces"),
                                            new RecipeIngredient("Onion", 1, "piece"),
                                            new RecipeIngredient("Bell Pepper", 1, "piece"),
                                            new RecipeIngredient("Cooking Oil", 2, "tablespoons"),
                                            new RecipeIngredient("Salt", 1, "teaspoon")
                                    ),
                                    "Cook the maize meal with water and salt until thick. "
                                            + "Fry onions, carrots, tomatoes and peppers in cooking oil. "
                                            + "Cook the vegetables until tender. "
                                            + "Serve the chakalaka with the pap."
                            ),

                            // 4. Samp and Beans
                            new Recipe(
                                    "samp_beans",
                                    "Samp and Beans",
                                    Arrays.asList(
                                            new RecipeIngredient("Samp", 2, "cups"),
                                            new RecipeIngredient("Beans", 1, "cup"),
                                            new RecipeIngredient("Onion", 1, "piece"),
                                            new RecipeIngredient("Cooking Oil", 2, "tablespoons"),
                                            new RecipeIngredient("Salt", 1, "teaspoon")
                                    ),
                                    "Soak the samp and beans before cooking. "
                                            + "Boil them together until tender. "
                                            + "Fry the onion in cooking oil and add it to the mixture. "
                                            + "Season with salt and cook until soft."
                            ),

                            // 5. Beef Potjie
                            new Recipe(
                                    "beef_potjie",
                                    "Beef Potjie",
                                    Arrays.asList(
                                            new RecipeIngredient("Beef", 1, "kg"),
                                            new RecipeIngredient("Potatoes", 4, "pieces"),
                                            new RecipeIngredient("Carrots", 4, "pieces"),
                                            new RecipeIngredient("Onion", 2, "pieces"),
                                            new RecipeIngredient("Tomato", 3, "pieces"),
                                            new RecipeIngredient("Cooking Oil", 3, "tablespoons"),
                                            new RecipeIngredient("Salt", 1, "teaspoon")
                                    ),
                                    "Brown the beef in cooking oil. "
                                            + "Add onions and tomatoes. "
                                            + "Layer the potatoes and carrots on top. "
                                            + "Add a little water and cover. "
                                            + "Cook slowly until the beef and vegetables are tender."
                            ),

                            // 6. Boerewors and Pap
                            new Recipe(
                                    "boerewors_pap",
                                    "Boerewors and Pap",
                                    Arrays.asList(
                                            new RecipeIngredient("Boerewors", 500, "g"),
                                            new RecipeIngredient("Maize Meal", 2, "cups"),
                                            new RecipeIngredient("Tomato", 2, "pieces"),
                                            new RecipeIngredient("Onion", 1, "piece"),
                                            new RecipeIngredient("Cooking Oil", 2, "tablespoons"),
                                            new RecipeIngredient("Salt", 1, "teaspoon")
                                    ),
                                    "Cook the maize meal with water and salt until thick. "
                                            + "Fry the onions and tomatoes in cooking oil. "
                                            + "Cook the boerewors until browned and fully cooked. "
                                            + "Serve the boerewors with pap and tomato relish."
                            ),

                            // 7. Vetkoek with Mince
                            new Recipe(
                                    "vetkoek_mince",
                                    "Vetkoek with Mince",
                                    Arrays.asList(
                                            new RecipeIngredient("Flour", 500, "g"),
                                            new RecipeIngredient("Beef Mince", 500, "g"),
                                            new RecipeIngredient("Onion", 1, "piece"),
                                            new RecipeIngredient("Tomato", 2, "pieces"),
                                            new RecipeIngredient("Cooking Oil", 500, "ml"),
                                            new RecipeIngredient("Salt", 1, "teaspoon")
                                    ),
                                    "Prepare a simple dough using flour, water and salt. "
                                            + "Shape the dough into small balls and fry until golden. "
                                            + "Fry the onion and beef mince with tomatoes. "
                                            + "Cut the vetkoek open and fill with the mince."
                            ),

                            // 8. Chicken Sosatie
                            new Recipe(
                                    "chicken_sosatie",
                                    "Chicken Sosatie",
                                    Arrays.asList(
                                            new RecipeIngredient("Chicken", 500, "g"),
                                            new RecipeIngredient("Onion", 2, "pieces"),
                                            new RecipeIngredient("Bell Pepper", 1, "piece"),
                                            new RecipeIngredient("Apricot Jam", 3, "tablespoons"),
                                            new RecipeIngredient("Curry Powder", 1, "tablespoon"),
                                            new RecipeIngredient("Cooking Oil", 2, "tablespoons")
                                    ),
                                    "Cut the chicken, onions and peppers into pieces. "
                                            + "Mix apricot jam, curry powder and cooking oil. "
                                            + "Coat the chicken with the mixture. "
                                            + "Place the chicken and vegetables onto skewers and grill until cooked."
                            ),

                            // 9. Malva Pudding
                            new Recipe(
                                    "malva_pudding",
                                    "Malva Pudding",
                                    Arrays.asList(
                                            new RecipeIngredient("Flour", 1, "cup"),
                                            new RecipeIngredient("Sugar", 1, "cup"),
                                            new RecipeIngredient("Eggs", 2, "pieces"),
                                            new RecipeIngredient("Milk", 1, "cup"),
                                            new RecipeIngredient("Butter", 100, "g"),
                                            new RecipeIngredient("Apricot Jam", 2, "tablespoons")
                                    ),
                                    "Beat the eggs and sugar together. "
                                            + "Add flour, milk, butter and apricot jam. "
                                            + "Mix into a smooth batter. "
                                            + "Bake until golden and serve warm with a sweet sauce."
                            ),

                            // 10. Cape Malay Chicken Curry
                            new Recipe(
                                    "cape_malay_chicken_curry",
                                    "Cape Malay Chicken Curry",
                                    Arrays.asList(
                                            new RecipeIngredient("Chicken", 500, "g"),
                                            new RecipeIngredient("Onion", 2, "pieces"),
                                            new RecipeIngredient("Potatoes", 3, "pieces"),
                                            new RecipeIngredient("Tomato", 2, "pieces"),
                                            new RecipeIngredient("Curry Powder", 2, "tablespoons"),
                                            new RecipeIngredient("Cooking Oil", 3, "tablespoons"),
                                            new RecipeIngredient("Salt", 1, "teaspoon")
                                    ),
                                    "Fry the onions in cooking oil. "
                                            + "Add the chicken and curry powder. "
                                            + "Add tomatoes and potatoes with some water. "
                                            + "Cover and simmer until the chicken and potatoes are tender."
                            ),

                            // 11. Spaghetti Bolognese
                            new Recipe(
                                    "spaghetti_bolognese",
                                    "Spaghetti Bolognese",
                                    Arrays.asList(
                                            new RecipeIngredient("Spaghetti", 500, "g"),
                                            new RecipeIngredient("Beef Mince", 500, "g"),
                                            new RecipeIngredient("Tomato", 4, "pieces"),
                                            new RecipeIngredient("Onion", 1, "piece"),
                                            new RecipeIngredient("Cooking Oil", 2, "tablespoons"),
                                            new RecipeIngredient("Salt", 1, "teaspoon")
                                    ),
                                    "Cook the spaghetti until tender. "
                                            + "Fry the onion and beef mince in cooking oil. "
                                            + "Add chopped tomatoes and simmer until the sauce thickens. "
                                            + "Season with salt and serve over the spaghetti."
                            ),

                            // 12. Chicken Fried Rice
                            new Recipe(
                                    "chicken_fried_rice",
                                    "Chicken Fried Rice",
                                    Arrays.asList(
                                            new RecipeIngredient("Rice", 2, "cups"),
                                            new RecipeIngredient("Chicken", 300, "g"),
                                            new RecipeIngredient("Eggs", 2, "pieces"),
                                            new RecipeIngredient("Carrots", 2, "pieces"),
                                            new RecipeIngredient("Peas", 1, "cup"),
                                            new RecipeIngredient("Onion", 1, "piece"),
                                            new RecipeIngredient("Cooking Oil", 3, "tablespoons")
                                    ),
                                    "Cook the rice and allow it to cool. "
                                            + "Cook the chicken in small pieces. "
                                            + "Fry the onion, carrots and peas in oil. "
                                            + "Add the chicken, rice and beaten eggs. "
                                            + "Stir-fry until everything is cooked."
                            ),

                            // 13. Beef Tacos
                            new Recipe(
                                    "beef_tacos",
                                    "Beef Tacos",
                                    Arrays.asList(
                                            new RecipeIngredient("Beef Mince", 500, "g"),
                                            new RecipeIngredient("Taco Shells", 8, "pieces"),
                                            new RecipeIngredient("Tomato", 2, "pieces"),
                                            new RecipeIngredient("Onion", 1, "piece"),
                                            new RecipeIngredient("Cheese", 200, "g"),
                                            new RecipeIngredient("Bell Pepper", 1, "piece"),
                                            new RecipeIngredient("Cooking Oil", 2, "tablespoons")
                                    ),
                                    "Fry the onion, bell pepper and beef mince in cooking oil. "
                                            + "Cook until the beef is fully cooked. "
                                            + "Warm the taco shells. "
                                            + "Fill the shells with the beef mixture, tomatoes and cheese."
                            ),

                            // 14. Chicken Curry
                            new Recipe(
                                    "chicken_curry",
                                    "Chicken Curry",
                                    Arrays.asList(
                                            new RecipeIngredient("Chicken", 500, "g"),
                                            new RecipeIngredient("Onion", 2, "pieces"),
                                            new RecipeIngredient("Tomato", 3, "pieces"),
                                            new RecipeIngredient("Potatoes", 2, "pieces"),
                                            new RecipeIngredient("Curry Powder", 2, "tablespoons"),
                                            new RecipeIngredient("Cooking Oil", 3, "tablespoons"),
                                            new RecipeIngredient("Salt", 1, "teaspoon")
                                    ),
                                    "Fry the onions in cooking oil. "
                                            + "Add chicken and curry powder. "
                                            + "Add tomatoes and potatoes with water. "
                                            + "Cover and simmer until the chicken and potatoes are cooked."
                            ),

                            // 15. Greek Salad
                            new Recipe(
                                    "greek_salad",
                                    "Greek Salad",
                                    Arrays.asList(
                                            new RecipeIngredient("Tomato", 3, "pieces"),
                                            new RecipeIngredient("Cucumber", 1, "piece"),
                                            new RecipeIngredient("Onion", 1, "piece"),
                                            new RecipeIngredient("Bell Pepper", 1, "piece"),
                                            new RecipeIngredient("Feta Cheese", 200, "g"),
                                            new RecipeIngredient("Olive Oil", 2, "tablespoons")
                                    ),
                                    "Chop the tomatoes, cucumber, onion and bell pepper. "
                                            + "Place the vegetables in a bowl. "
                                            + "Add feta cheese and olive oil. "
                                            + "Mix gently and serve fresh."
                            )
                    );

                    for (Recipe recipe : recipes) {

                        db.collection("recipes")
                                .document(recipe.getId())
                                .set(recipe);
                    }
                });
    }
}