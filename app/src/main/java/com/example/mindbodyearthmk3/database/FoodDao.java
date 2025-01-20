package com.example.mindbodyearthmk3.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM Food WHERE mealId = :mealId")
    List<Food> findByMealId(long mealId);

    @Insert
    long insert(Food food);

    @Delete
    void delete(Food food); // Add this method to delete a specific Food entity

    @Query("DELETE FROM Food WHERE foodId = :foodId")
    void deleteById(long foodId);

    @Query("DELETE FROM Food WHERE mealId = :mealId")
    void deleteByMealId(long mealId);

    @Query("DELETE FROM Food")
    void deleteAll();
}
