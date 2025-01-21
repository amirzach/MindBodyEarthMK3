package com.example.mindbodyearthmk3.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HealthData {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String date;
    private int bloodPressure;
    private int heartRate;
    private float weight;

    public HealthData(String date, int bloodPressure, int heartRate, float weight) {
        this.date = date;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.weight = weight;
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public int getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(int bloodPressure) { this.bloodPressure = bloodPressure; }
    public int getHeartRate() { return heartRate; }
    public void setHeartRate(int heartRate) { this.heartRate = heartRate; }
    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }
}
