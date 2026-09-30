# Smart Pantry Manager

## Project Overview

Smart Pantry Manager is a Java Android application that helps users manage their pantry ingredients and find recipes that can be made using the ingredients they currently have.

The application uses Firebase Cloud Firestore to store pantry items and recipes. It also includes expiry reminders to help users keep track of ingredients that are close to expiring.

## Features

* Add, edit and delete pantry ingredients
* Store ingredient name, quantity, unit and expiry date
* View saved pantry ingredients using a RecyclerView
* 15 pre-loaded recipes stored in Firebase Firestore
* Strict recipe matching based on available ingredients and quantities
* View full recipe ingredients and preparation steps
* Expiry reminders for items that are expired or expiring soon
* Settings screen to enable or disable expiry reminders
* Input validation when adding or editing ingredients
* Navigation between the different application screens
* Persistent data storage using Firebase Firestore

## Technologies Used

* Java
* Android Studio
* XML
* Firebase Cloud Firestore
* RecyclerView
* SharedPreferences
* Git and GitHub

## Main Screens

The application includes:

* **Pantry/Home Screen** – Displays the user's saved pantry ingredients and expiry reminders.
* **Add/Edit Ingredient Screen** – Allows users to add new ingredients or update existing ones.
* **Suggested Recipes Screen** – Shows recipes that can currently be made using the user's pantry.
* **Recipe Detail Screen** – Shows the ingredients and preparation steps for a selected recipe.
* **Settings Screen** – Allows users to turn expiry reminders on or off.

## Recipe Matching

The application uses strict recipe matching.

A recipe is only suggested when all of its required ingredients are available in the user's pantry and the available quantities are sufficient.

For example, if a recipe requires five ingredients and the user only has four, that recipe will not be shown as a suggestion.

The application also performs basic ingredient and unit handling to make matching more practical for normal pantry entries.

## Database

Firebase Cloud Firestore is used as the application's database.

The main collections are:

* **pantryItems** – Stores the user's pantry ingredients, quantities, units and expiry dates.
* **recipes** – Stores the pre-loaded recipes, including their ingredients and preparation steps.

Pantry data is stored in Firestore so that it remains available when the application is closed and reopened.

## Expiry Reminders

The application checks pantry items that have an expiry date.

When expiry reminders are enabled, the Home screen displays items that are expired or within seven days of their expiry date.

The reminder setting is stored using SharedPreferences so that the user's preference is remembered.

## Recipe Seeding

The application automatically adds the initial recipe collection to Firestore when needed.

The seeding process checks whether the recipes already exist before adding them, preventing duplicate recipes from being created every time the application starts.

## Navigation

Android Intents are used to navigate between the different activities.

The main screens are the Pantry/Home screen, Add/Edit Ingredient screen, Suggested Recipes screen, Recipe Detail screen and Settings screen.

## Input Validation

The Add/Edit Ingredient screen validates the information entered by the user before saving it.

Required information such as the ingredient name and quantity must be provided before an ingredient can be saved.

## Project Scope

The application focuses only on the user's pantry and recipe matching.

The project does not use Google Maps, mapping SDKs, GPS or device location services.

## How to Run

1. Clone or download the repository.
2. Open the project in Android Studio.
3. Configure Firebase for the project.
4. Add the required `google-services.json` file to the `app` folder.
5. Allow Android Studio to complete the Gradle sync.
6. Start an Android emulator or connect an Android device.
7. Run the application from Android Studio.

## Firebase Configuration

The Firebase configuration file is not included in the GitHub repository.

A Firebase project with Cloud Firestore enabled is required to run the application.

## GitHub

The project was developed incrementally using Git and GitHub, with multiple commits showing the development of the application's features and improvements.

## Future Improvements

Possible future improvements include:

* Adding more recipes
* Improved ingredient matching
* Recipe search and filtering
* Recipe favourites
* More advanced expiry notifications
* Nutritional information for recipes

