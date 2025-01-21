package com.example.mindbodyearthmk3.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HealthDataDao {
    @Insert
    void insert(HealthData healthData);

    @Query("SELECT * FROM HealthData ORDER BY date ASC")
    List<HealthData> getAll();
}
