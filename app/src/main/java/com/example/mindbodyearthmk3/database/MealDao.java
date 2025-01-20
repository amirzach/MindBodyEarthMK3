package com.example.mindbodyearthmk3.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MealDao {
    @Query("SELECT * FROM Meal WHERE mealPlanId = :mealPlanId")
    List<Meal> findByMealPlanId(long mealPlanId);

    @Query("SELECT * FROM Meal WHERE mealId = :mealId")
    Meal findById(long mealId);

    @Insert
    long insert(Meal meal);

    @Update
    void update(Meal meal);

    @Query("DELETE FROM Meal WHERE mealId = :mealId")
    void deleteById(long mealId);

    @Query("DELETE FROM Meal WHERE mealPlanId = :mealPlanId")
    void deleteByMealPlanId(long mealPlanId);

    @Query("DELETE FROM Meal")
    void deleteAll();
}
