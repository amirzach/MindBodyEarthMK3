package com.example.mindbodyearthmk3.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WorkoutPlanDao {
    @Insert
    void insertWorkoutPlan(WorkoutPlan workoutPlan);

    @Update
    void updateWorkoutPlan(WorkoutPlan workoutPlan);

    @Delete
    void deleteWorkoutPlan(WorkoutPlan workoutPlan);

    @Query("SELECT * FROM WorkoutPlan")
    List<WorkoutPlan> allWorkoutPlans();

    @Transaction
    @Query("SELECT * FROM WorkoutPlan")
    public List<WorkoutPlanWithWorkouts> getWorkoutPlansWithWorkouts();

    @Transaction
    @Query("SELECT * FROM Workout WHERE workout_id = :id LIMIT 1")
    Workout getWorkoutByWorkoutPlanId(int id);

//    @Transaction
//    @Query("SELECT * FROM WorkoutPlan WHERE dayId IN (SELECT dayId FROM days WHERE date < :currentDate)")
//    List<WorkoutPlanWithWorkouts> findPastWorkoutPlans(String currentDate);
}

