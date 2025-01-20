package com.example.mindbodyearthmk3.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import java.util.List;

@Entity
public class MealPlan {
    @PrimaryKey(autoGenerate = true)
    private long mealPlanId;
    private String day;

    // Getters and setters
    public long getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(long mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
