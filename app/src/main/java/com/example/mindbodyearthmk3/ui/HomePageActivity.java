package com.example.mindbodyearthmk3.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mindbodyearthmk3.R;

public class HomePageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Logout button
        findViewById(R.id.btnLogout).setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
            startActivity(intent);
        });

        // Journal button
        findViewById(R.id.btnJournal).setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, JournalActivity.class);
            startActivity(intent);
        });

        // Meal Plan button
        findViewById(R.id.btnMealPlan).setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, MealPlanActivity.class);
            startActivity(intent);
        });

        // Workout Plan button
        findViewById(R.id.btnWorkoutPlan).setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, WorkoutPlanActivity.class);
            startActivity(intent);
        });

        // Meditation Guides button
        findViewById(R.id.btnMeditationGuides).setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, MeditationGuidesActivity.class);
            startActivity(intent);
        });

        // Carbon Footprint button
        findViewById(R.id.btnCarbonFootprint).setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, CarbonFootprintActivity.class);
            startActivity(intent);
        });

        // Health Tracking Status button
        findViewById(R.id.btnHealthTrack).setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, HealthTrackingActivity.class);
            startActivity(intent);
        });
    }
}
