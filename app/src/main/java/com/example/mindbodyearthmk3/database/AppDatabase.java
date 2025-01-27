package com.example.mindbodyearthmk3.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.mindbodyearthmk3.database.carbonfootprint.CarbonFootprint;
import com.example.mindbodyearthmk3.database.carbonfootprint.CarbonFootprintDao;

// Specify the database entities and version
@Database(entities = {
        User.class,
        Days.class,
        JournalEntry.class,
        MealPlan.class,
        Meal.class,
        Food.class,
        HealthData.class,
        Workout.class,
        CarbonFootprint.class
},
        version = 8, // Increment version number for the new entity
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract UserDao userDao();
    public abstract DaysDao daysDao();
    public abstract JournalEntryDao journalEntryDao();
    public abstract MealPlanDao mealPlanDao();
    public abstract MealDao mealDao();
    public abstract FoodDao foodDao();
    public abstract HealthDataDao healthDataDao(); // New DAO
    public abstract WorkoutDao workoutDao();

    public abstract CarbonFootprintDao carbonFootprintDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            initializeDatabase(context);
        }
        return instance;
    }

    public static synchronized void initializeDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration() // Handle migration by recreating the database
                    .build();
        }
    }
}
