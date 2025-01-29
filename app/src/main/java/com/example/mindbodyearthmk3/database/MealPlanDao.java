package com.example.mindbodyearthmk3.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mindbodyearthmk3.database.carbonfootprint.CarbonFootprint;

import java.util.List;

@Dao
public interface MealPlanDao {
    @Query("SELECT * FROM MealPlan")
    List<MealPlan> getAll();

    @Query("SELECT * FROM MealPlan WHERE day = :day")
    MealPlan findByDay(String day);

    @Insert
    long insert(MealPlan mealPlan);

    @Query("DELETE FROM MealPlan WHERE mealPlanId = :mealPlanId")
    void deleteById(long mealPlanId);

    @Query("DELETE FROM MealPlan")
    void deleteAll();

    @Query("SELECT SUM(totalCalories) FROM Meal WHERE mealPlanId = :mealPlanId")
    int getMealPlanTotalCalories(long mealPlanId);

    @Query("SELECT mealPlanId FROM MealPlan ORDER BY mealPlanId DESC LIMIT 1")
    long getCurrentMealPlanId();

    @Query("SELECT * FROM MealPlan WHERE mealPlanId = :mealPlanId")
    MealPlan getMealPlan(long mealPlanId);

    @Insert
    void saveCarbonFootprint(CarbonFootprint carbonFootprint);
}
