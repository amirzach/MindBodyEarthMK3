package com.example.mindbodyearthmk3.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

// Specify the database entities and version
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // Singleton instance of the database
    private static volatile AppDatabase instance;

    // Abstract method to access the DAO
    public abstract UserDao userDao();

    // Singleton method to get the database instance
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "user_database")
                    .fallbackToDestructiveMigration() // Automatically recreate the database if migrations are not defined
                    .build();
        }
        return instance;
    }
}
