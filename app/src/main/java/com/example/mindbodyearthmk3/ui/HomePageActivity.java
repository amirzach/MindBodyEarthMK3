package com.example.mindbodyearthmk3.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mindbodyearthmk3.R;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

//        Intent intent = new Intent(this, WorkoutPlanActivity.class);
//
//        findViewById(R.id.bubble_workout_plan).setOnClickListener(v -> {
//            startActivity(intent);
//        });

        //Logout button clicked
        findViewById(R.id.btnLogout).setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
            startActivity(intent);
        });

        //Bubbles clicked
        onBubbleClick(WorkoutPlanActivity.class, R.id.bubble_workout_plan);
        onBubbleClick(JournalActivity.class, R.id.bubble_journal);
        onBubbleClick(MealPlanActivity.class, R.id.bubble_meal_plan);
        onBubbleClick(TipsChatbotActivity.class, R.id.bubble_tips);
        onBubbleClick(HealthTrackingActivity.class, R.id.bubble_health_tracking);
        onBubbleClick(CarbonFootprintActivity.class, R.id.bubble_carbon_footprint);

    }

    private void onBubbleClick(Class<?> activityClass, int bubbleId) {
        findViewById(bubbleId).setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, activityClass);
            startActivity(intent);
        });
    }
}