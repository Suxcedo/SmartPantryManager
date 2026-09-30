package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchExpiryReminders;

    private SharedPreferences preferences;

    private static final String PREFS_NAME =
            "SmartPantryPreferences";

    private static final String EXPIRY_REMINDERS =
            "expiryReminders";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        switchExpiryReminders =
                findViewById(R.id.switchExpiryReminders);

        preferences =
                getSharedPreferences(
                        PREFS_NAME,
                        MODE_PRIVATE
                );

        boolean remindersEnabled =
                preferences.getBoolean(
                        EXPIRY_REMINDERS,
                        true
                );

        switchExpiryReminders.setChecked(
                remindersEnabled
        );

        switchExpiryReminders.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    preferences
                            .edit()
                            .putBoolean(
                                    EXPIRY_REMINDERS,
                                    isChecked
                            )
                            .apply();
                }
        );
    }
}