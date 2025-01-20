package com.example.mindbodyearthmk3.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

// Specify the database entities and version
@Database(entities = {User.class, Days.class, JournalEntry.class, MealPlan.class, Meal.class, Food.class},
        version = 4, // Increment version number
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract UserDao userDao();
    public abstract DaysDao daysDao();
    public abstract JournalEntryDao journalEntryDao();
    public abstract MealPlanDao mealPlanDao(); // New DAO
    public abstract MealDao mealDao();         // New DAO
    public abstract FoodDao foodDao();         // New DAO

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration() // Handle migration by recreating the database
                    .build();
        }
        return instance;
    }
}
