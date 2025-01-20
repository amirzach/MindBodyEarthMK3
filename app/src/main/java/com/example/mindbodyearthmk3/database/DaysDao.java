package com.example.mindbodyearthmk3.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface DaysDao {

    @Insert
    void insert(Days day);

    @Query("SELECT * FROM days WHERE date = :date LIMIT 1")
    Days findByDate(String date);

    @Query("SELECT * FROM days")
    List<Days> getAllDays();
}
