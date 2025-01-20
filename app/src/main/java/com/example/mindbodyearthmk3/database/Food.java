package com.example.mindbodyearthmk3.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Food {
    @PrimaryKey(autoGenerate = true)
    private long foodId;
    private String foodName;
    private String mainNutrient;
    private int calories;
    private long mealId;

    // Getters and setters
    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getMainNutrient() {
        return mainNutrient;
    }

    public void setMainNutrient(String mainNutrient) {
        this.mainNutrient = mainNutrient;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }
}
