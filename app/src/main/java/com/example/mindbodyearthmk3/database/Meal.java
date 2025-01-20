package com.example.mindbodyearthmk3.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Meal {
    @PrimaryKey(autoGenerate = true)
    private long mealId;
    private int totalCalories;
    private String timeOfDayConsumed;
    private String day;
    private long mealPlanId;

    // Getters and setters
    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getTimeOfDayConsumed() {
        return timeOfDayConsumed;
    }

    public void setTimeOfDayConsumed(String timeOfDayConsumed) {
        this.timeOfDayConsumed = timeOfDayConsumed;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(long mealPlanId) {
        this.mealPlanId = mealPlanId;
    }
}
