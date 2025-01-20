package com.example.mindbodyearthmk3.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "days")
public class Days {

    @PrimaryKey(autoGenerate = true)
    private int dayId;

    private String day;
    private String date;

    // Constructor
    public Days(String day, String date) {
        this.day = day;
        this.date = date;
    }

    // Getters and Setters
    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
